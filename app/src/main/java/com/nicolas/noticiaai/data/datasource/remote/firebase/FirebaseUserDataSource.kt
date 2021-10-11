package com.nicolas.noticiaai.data.datasource.remote.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.nicolas.noticiaai.common.User
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FirebaseUserDataSource @Inject constructor(
    firebaseFirestore: FirebaseFirestore,
    firebaseStorage: FirebaseStorage,
    firebaseAuth: FirebaseAuth
) : UserDataSource {

    private val documentReference =
        firebaseFirestore.document("noticiaai/users")
    private val storageReference = firebaseStorage.reference
    private val auth = firebaseAuth.currentUser!!.uid

    override suspend fun getUser(): List<User> {
        return suspendCoroutine { continuation ->
            val userReference = documentReference.collection("user")
            val users = mutableListOf<User>()
            userReference.get().addOnSuccessListener { user ->
                for (document in user) {
                    document.toObject(User::class.java).run {
                        users.add(this)
                    }
                }
                continuation.resumeWith(Result.success(users))
            }
            userReference.get().addOnFailureListener { exception ->
                continuation.resumeWith(Result.failure(exception))
            }
        }
    }

    override suspend fun uploadUserImage(imageUri: Uri): String {
        return suspendCoroutine { continuation ->
            val randomKey = UUID.randomUUID()
            val childReference = storageReference.child(
                "images/$randomKey"
            )
            childReference.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        val path = uri.toString()
                        continuation.resumeWith(Result.success(path))
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }

    override suspend fun createUser(user: User): User {
        return suspendCoroutine { continuation ->
            documentReference
                .collection("user")
                .document(auth)
                .set(user, SetOptions.merge())
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(user))
                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }

}
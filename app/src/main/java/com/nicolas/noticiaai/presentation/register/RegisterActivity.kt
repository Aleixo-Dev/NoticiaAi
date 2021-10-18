package com.nicolas.noticiaai.presentation.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.nicolas.noticiaai.databinding.ActivityRegisterBinding
import com.nicolas.noticiaai.MainActivity
import com.nicolas.noticiaai.common.Constants
import com.nicolas.noticiaai.common.LoadingUtils
import com.nicolas.noticiaai.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.clearPersistence()

        File(this.cacheDir.path).deleteRecursively()

        binding.tvEnterAccount.setOnClickListener {
            goToLoginAccount()
        }

        //TODO: Refatorar tudo.
        binding.apply {
            buttonRegister.setOnClickListener {
                if (inputName.text.isNullOrEmpty().not()) {
                    if (inputEmail.text.isNullOrEmpty().not()) {
                        if (inputPassword.text.isNullOrEmpty().not()) {
                            LoadingUtils.showDialog(this@RegisterActivity, true)
                            registerAccount(
                                inputEmail.text.toString(),
                                inputPassword.text.toString()
                            )
                            saveValueAndKey()
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Informe a senha.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Informe o e-mail.", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Informe o nome.", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun goToLoginAccount() = binding.apply {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        finish()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun saveValueAndKey() = binding.apply {

        val sharedPref = getSharedPreferences(Constants.USER_NAME_APP, Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString(Constants.USER_NAME, binding.inputName.text.toString())
            .apply()
    }

    private fun registerAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    LoadingUtils.hideDialog()
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }.addOnFailureListener {
                when (it) {
                    is FirebaseAuthWeakPasswordException -> {
                        binding.inputPassword.error = "Senha fraca."
                    }
                    is FirebaseAuthUserCollisionException -> {
                        Toast.makeText(
                            this,
                            "E-mail já registrado.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is FirebaseAuthEmailException -> {
                        Toast.makeText(
                            this,
                            "Favor informe um e-mail válido.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
}
package com.nicolas.noticiaai.common

import com.google.firebase.firestore.PropertyName
import java.io.Serializable

data class User(
    val id: String = "",
    val name: String = "",
    @get:PropertyName("image_url")
    @set:PropertyName("image_url")
    var image: String = ""
) : Serializable

package com.nicolas.noticiaai.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.nicolas.noticiaai.MainActivity
import com.nicolas.noticiaai.R
import com.nicolas.noticiaai.common.showToast
import com.nicolas.noticiaai.databinding.ActivityLoginBinding
import com.nicolas.noticiaai.presentation.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvCreateNewAccount.setOnClickListener {
            goToCreateNewAccount()
        }

        binding.apply {
            buttonLogin.setOnClickListener {
                if (inputEmailLogin.text.isNullOrEmpty().not()) {
                    if (inputPasswordLogin.text.isNullOrEmpty().not()) {
                        loginAccount(
                            inputEmailLogin.text.toString(),
                            inputPasswordLogin.text.toString()
                        )
                    } else {
                        showToast("Informe a senha.")
                    }
                } else {
                    showToast("Informe o e-mail.")
                }
            }
        }
    }

    private fun goToCreateNewAccount() = binding.apply {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        finish()
    }

    private fun loginAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }.addOnFailureListener {
            }
    }
}
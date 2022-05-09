package com.example.restaurantreviewapp

import android.R.attr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

import com.google.firebase.auth.FirebaseUser

import com.google.firebase.auth.AuthResult

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener

import android.R.attr.password
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethod
import com.google.android.material.snackbar.Snackbar
import android.app.Activity
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager


class LoginActivity : AppCompatActivity() {
    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)


        val emailText = findViewById<EditText>(R.id.input_email)
        val passwordText = findViewById<EditText>(R.id.input_password)

        val button= findViewById<Button>(R.id.login)
        val registerButton = findViewById<Button>(R.id.register)


        emailText.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })

        passwordText.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })


        button.setOnClickListener { view ->
            mAuth.signInWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val intent = Intent(this, MainActivity:: class.java)
                        intent.putExtra("id", user?.email)
                        startActivity(intent)
                    } else {
                        showMessage(view, "Couldn't sign you in")
                    }
                }
        }


        registerButton.setOnClickListener { _ -> startActivity(Intent(this, RegisterActivity:: class.java))}


    }


    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

}
package com.example.restaurantreviewapp

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

import com.google.firebase.auth.FirebaseUser

import com.google.firebase.auth.AuthResult

import androidx.annotation.NonNull
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity: AppCompatActivity() {
    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_view)

        val emailText = findViewById<EditText>(R.id.input_email)
        val passwordText = findViewById<EditText>(R.id.input_password)


        val button = findViewById<Button>(R.id.register)


        emailText.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })

        passwordText.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })

        button.setOnClickListener { view ->
            mAuth.createUserWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                .addOnCompleteListener(this
                ) { task ->
                    if (task.isSuccessful) {


                        Log.d("Cont", "merge")

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {

                        showMessage(view, "Couldn't create an account")
                    }
                }
        }
    }


    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }



    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}
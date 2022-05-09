package com.example.restaurantreviewapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class WriteReviewActivity : AppCompatActivity() {
    val REQUEST_CODE = 100
    var storage = Firebase.storage
    val db = Firebase.firestore
    var ImageUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.write_review)

        val submitButton = findViewById<Button>(R.id.submit_review_button)

        val title = findViewById<EditText>(R.id.review_title)
        val comment = findViewById<EditText>(R.id.review_comment)


        title.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })

        comment.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })

        submitButton.setOnClickListener { _ ->

            uploadFile()
            startActivity(Intent(this, MainActivity::class.java))
        }


        val imageToUpload = findViewById<ImageView>(R.id.image_to_upload)

        imageToUpload.setOnClickListener{ _ -> openGalleryForImage()}

        val items = listOf("Casual Dining", "Fast Food ", "Cafe")
        val adapter = ArrayAdapter(this, R.layout.overflow_menu, items)

        val textField = findViewById<AutoCompleteTextView>(R.id.autoCompleteField)

        textField.setAdapter(adapter)
    }


    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun uploadReviewMetadata(imagePath: String) {
        val title = findViewById<EditText>(R.id.review_title)
        val rating = findViewById<RatingBar>(R.id.review_rating)
        val category = findViewById<AutoCompleteTextView>(R.id.autoCompleteField)
        val comment = findViewById<EditText>(R.id.review_comment)


        Log.i("title", title.text.toString())
        Log.i("rating", rating.rating.toString())
        Log.i("category", category.text.toString())
        Log.i("comment", comment.text.toString())

        val review = hashMapOf(
            "title" to title.text.toString(),
            "rating" to rating.rating.toDouble(),
            "category" to category.text.toString(),
            "comment" to comment.text.toString(),
            "imagePath" to imagePath
        )

        db.collection("reviews")
            .add(review)
            .addOnSuccessListener { documentReference ->
                Log.d("WriteReview", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("WriteReview", "Error adding document", e)
            }
    }

    private fun uploadFile() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$filename")


        val uploadTask = storageReference!!.putFile(ImageUri!!)

        val task = uploadTask.continueWithTask {
                task ->
                if(!task.isSuccessful)
                {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            storageReference!!.downloadUrl
        }.addOnCompleteListener { task ->
            if(task.isSuccessful)
            {
                val downloadUri = task.result
                val url = downloadUri.toString()
                Log.d("DIRECTLINK", url)


                uploadReviewMetadata(url)
            }
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {


            val imageUploadButton = findViewById<ImageView>(R.id.image_to_upload)

            ImageUri = data?.data!!

            imageUploadButton.setImageURI(data?.data) // handle chosen image
        }
    }

}
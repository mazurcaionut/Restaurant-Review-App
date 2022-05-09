package com.example.restaurantreviewapp.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantreviewapp.*
import com.example.restaurantreviewapp.R
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_reviews.*

class ReviewsFragment : Fragment() {
    private lateinit var db: FirebaseFirestore
    private lateinit var restaurantRecyclerView: RecyclerView
    private lateinit var restaurantArrayList: ArrayList<RestaurantModel>
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_reviews, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restaurantRecyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
        restaurantRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        restaurantRecyclerView.setHasFixedSize(true)


        restaurantArrayList = arrayListOf()

        restaurantAdapter = RestaurantAdapter(restaurantArrayList)


        restaurantRecyclerView.adapter = restaurantAdapter

        getRestaurantData(view)
    }


    private fun getRestaurantData(view: View) {
        db = FirebaseFirestore.getInstance()

        db.collection("reviews").addSnapshotListener(object: EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if(error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return
                }

                for(dc: DocumentChange in value?.documentChanges!!) {

                    if(dc.type == DocumentChange.Type.ADDED) {

                        var restaurantModel = RestaurantModel()

                        restaurantModel.setTitle(dc.document.data.getValue("title").toString())
                        restaurantModel.setComment(dc.document.data.getValue("comment").toString())
                        restaurantModel.setRating(dc.document.data.getValue("rating").toString().toDouble())
                        restaurantModel.setImage(dc.document.data.getValue("imagePath").toString())
                        restaurantModel.setCategory(dc.document.data.getValue("category").toString())

                        restaurantArrayList.add(restaurantModel)

                    }

                }

                restaurantAdapter.notifyDataSetChanged()

            }

        })

    }
}
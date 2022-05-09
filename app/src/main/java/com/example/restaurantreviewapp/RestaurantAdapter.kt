package com.example.restaurantreviewapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantreviewapp.fragments.ReviewsFragment
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class RestaurantAdapter(private val imageModelArrayList: MutableList<RestaurantModel>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    /*
     * Inflate our views using the layout defined in row_layout.xml
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.review_layout, parent, false)

        return ViewHolder(v)
    }

    /*
     * Bind the data to the child views of the ViewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]


        holder.title.text = info.getTitle()
        holder.comment.text = info.getComment()
        holder.rating.rating = info.getRating().toFloat()
        holder.category.text = info.getCategory()

        Picasso.get().load(info.getImage()).placeholder(R.drawable.progress_animation).into(holder.image)
    }


    /*
     * Get the maximum size of the
     */
    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    /*
     * The parent class that handles layout inflation and child view use
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var title = itemView.findViewById<View>(R.id.review_layout_title) as TextView
        var rating = itemView.findViewById<View>(R.id.review_layout_rating) as RatingBar
        var comment = itemView.findViewById<View>(R.id.review_layout_comment) as TextView
        var image = itemView.findViewById<View>(R.id.review_layout_image) as ImageView
        var category = itemView.findViewById<View>(R.id.review_layout_category) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val msg = title.text
            val snackbar = Snackbar.make(v, "$msg", Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }
}
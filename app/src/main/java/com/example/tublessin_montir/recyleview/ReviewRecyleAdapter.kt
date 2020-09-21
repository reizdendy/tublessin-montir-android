package com.example.tublessin_montir.recyleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.montir.MontirRating

class ReviewRecyleAdapter(private val reviewList: List<MontirRating>) :
    RecyclerView.Adapter<ReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rating_recycle_view, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.review.text = reviewList[position].review
//       holder.rating.text = reviewList[position].rating.toString()
        holder.rating.rating = reviewList[position].rating.toInt().toFloat()
        holder.date.text = reviewList[position].date_created
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }
}

class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val review = view.findViewById<TextView>(R.id.content_review)
    //    val rating = view.findViewById<TextView>(R.id.rating_review)
    val date = view.findViewById<TextView>(R.id.date_review)
    val rating = view.findViewById<RatingBar>(R.id.rating_bar_indikator)

}

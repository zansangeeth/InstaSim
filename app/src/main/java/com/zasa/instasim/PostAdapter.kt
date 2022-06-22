package com.zasa.instasim

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zasa.instasim.models.Post
import kotlinx.android.synthetic.main.item_post.view.*

/**
 **@Project -> InstaSim
 **@Author -> Sangeeth on 6/23/2022
 */
class PostAdapter(val context: Context, val posts : List<Post>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(post: Post) {
            itemView.tvName.text = post.user?.username
            itemView.tvDescription.text = post.description
            Glide.with(context).load(post.imageUrl).into(itemView.ivPost)
            itemView.tvTime.text = DateUtils.getRelativeTimeSpanString(post.createdTimeInMs)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size
}
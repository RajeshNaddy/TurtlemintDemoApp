package com.turtlemint.assignment.ui.fragments.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.turtlemint.assignment.data.model.Comment
import com.turtlemint.assignment.databinding.RowCommentsBinding

class CommentRecyclerAdapter() : ListAdapter<Comment,CommentsViewHolder>(CommentItemDiffCallback())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = RowCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: CommentsViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] == true) {
                holder.updateViews(getItem(position))
            }
        }
    }
}

class CommentItemDiffCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(
        oldItem: Comment,
        newItem: Comment
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Comment,
        newItem: Comment
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Comment, newItem: Comment): Any? {
        return if (oldItem.updated_at != newItem.updated_at || oldItem.id != newItem.id) true else null
    }
}

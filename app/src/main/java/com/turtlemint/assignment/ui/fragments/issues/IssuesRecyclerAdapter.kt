package com.turtlemint.assignment.ui.fragments.issues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.turtlemint.assignment.data.model.Issues
import com.turtlemint.assignment.databinding.RowIssuesBinding

class IssuesRecyclerAdapter(private val interaction: IssuesInteraction) :
    ListAdapter<Issues, IssuesViewHolder>(IssuesItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesViewHolder {
        val binding = RowIssuesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssuesViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: IssuesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: IssuesViewHolder,
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

interface IssuesInteraction {
    fun onIssueClicked(view: View, issue: Issues)
}

class IssuesItemDiffCallback : DiffUtil.ItemCallback<Issues>() {
    override fun areItemsTheSame(
        oldItem: Issues,
        newItem: Issues
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Issues,
        newItem: Issues
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Issues, newItem: Issues): Any? {
        return if (oldItem.updated_at != newItem.updated_at || oldItem.id != newItem.id) true else null
    }
}
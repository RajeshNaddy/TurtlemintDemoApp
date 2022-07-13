package com.turtlemint.assignment.ui.fragments.issues

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.turtlemint.assignment.R
import com.turtlemint.assignment.data.model.Issues
import com.turtlemint.assignment.databinding.RowIssuesBinding
import com.turtlemint.assignment.util.IssueStateHelperEnum
import com.turtlemint.assignment.util.showIf
import com.turtlemint.assignment.util.toFormattedDateString

class IssuesViewHolder constructor(
    private val binding: RowIssuesBinding,
    private val interaction: IssuesInteraction
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(issue: Issues) = with(binding) {
        itemView.setOnClickListener {
            interaction.onIssueClicked(root, issue)
        }
        root.transitionName = binding.root.context.getString(R.string.transition_name_issue,issue.id)
        tvIssueNumber.text = "#${issue.number}"
        tvTitle.text = issue.title
        tvUsername.text = issue.user.login
        ivAvatar.load(issue.user.avatar_url){
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        updateViews(issue)
    }

     fun updateViews(issue: Issues) {
        binding.apply {
            tvUpdatedOn.text = issue.updated_at
            tvDescription.showIf(!issue.body.isNullOrBlank()) { isVisible ->
                if (isVisible) {
                    tvDescription.text = issue.body?.trim()
                }
            }
            ivStatus.setImageResource(IssueStateHelperEnum.getIssueStateEnum(issue.state).resId)
            tvUpdatedOn.apply {
                text = context.getString(R.string.label_issue_update, issue.updated_at.toFormattedDateString())
            }
        }
    }

}
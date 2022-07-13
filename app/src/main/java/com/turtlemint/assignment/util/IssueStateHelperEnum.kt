package com.turtlemint.assignment.util

import androidx.annotation.DrawableRes
import com.turtlemint.assignment.R

enum class IssueStateHelperEnum(val state: String, @DrawableRes val resId: Int) {
    OPEN("open", R.drawable.ic_open),
    CLOSE("closed", R.drawable.ic_closed),
    ALL("all", R.drawable.ic_open);

    companion object {
        private val map = values().associateBy(IssueStateHelperEnum::state)
        fun getIssueStateEnum(state: String):IssueStateHelperEnum = map[state] ?: OPEN
    }
}
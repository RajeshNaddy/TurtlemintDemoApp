package com.turtlemint.assignment.util

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.core.content.res.use
import androidx.fragment.app.Fragment
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone(makeInvisible: Boolean = false) {
    visibility = if (makeInvisible) View.INVISIBLE else View.GONE
}

fun View.showIf(shouldShow: Boolean, isVisible: (Boolean) -> Unit) {
    visibility = if (shouldShow) View.VISIBLE else View.GONE
    isVisible(shouldShow)
}

fun Context.themeColor(
    @AttrRes colorAttrId: Int
): Int {
    return obtainStyledAttributes(
        intArrayOf(colorAttrId)
    ).use {
        it.getColor(0, Color.BLACK)
    }
}

fun Fragment.showToast(text: String?){
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun String.toFormattedDateString(format: String = "MM-dd-yyyy"): String {
    val instant: Instant = Instant.parse(this)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault())
    return localDateTime.format(DateTimeFormatter.ofPattern(format))
}

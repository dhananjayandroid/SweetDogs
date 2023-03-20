@file:Suppress("unused")

package com.djay.sweetdogs.utils


import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Shorthand extension function to make view gone
 */
fun View.makeGone() {
    this.visibility = View.GONE
}

/**
 * Shorthand extension function to make view visible
 */
fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

internal fun Fragment.showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
        show()
    }
}

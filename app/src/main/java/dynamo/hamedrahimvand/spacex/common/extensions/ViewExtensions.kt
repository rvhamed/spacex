package dynamo.hamedrahimvand.spacex.common.extensions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
fun ImageView.loadUrl(
    url: String?,
    placeholder: Int = android.R.color.transparent,
    error: Int = android.R.color.transparent,
) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .error(error)
        .override(300,300)
        .into(this)
}

fun View.hide(keepSpace: Boolean = true) {
    if (visibility == View.VISIBLE) {
        visibility = if (keepSpace) {
            View.INVISIBLE
        } else {
            View.GONE
        }
    }
}

fun View.show(): View {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
    return this
}
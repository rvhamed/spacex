package dynamo.hamedrahimvand.spacex.common.extensions

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

fun Activity.showSnack(
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    param: (SnackbarParam.() -> Unit)? = null
): Snackbar? {
    val view = findViewById<View>(android.R.id.content)
    view?.let {
        val snack = Snackbar.make(view, message, length)
        if (param != null) {
            val snackbarParam = SnackbarParam()
            snackbarParam.param()
            snack.setAction(snackbarParam.actionText ?: "") {
                snackbarParam.actionBody.invoke()
            }
            snackbarParam.actionTextColorResource?.let {
                snack.setActionTextColor(ContextCompat.getColor(this, it))
            }
        }

        snack.show()
        return snack
    }
    return null
}

class SnackbarParam {
    var actionText: String? = null
    var actionTextColorResource: Int? = null
    var actionBody: () -> Unit = {}
}
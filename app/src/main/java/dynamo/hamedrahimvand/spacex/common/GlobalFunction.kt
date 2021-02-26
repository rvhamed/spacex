package dynamo.hamedrahimvand.spacex.common

import android.content.Context
import android.util.TypedValue

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
fun dpToPx(context: Context, dp: Float) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    )

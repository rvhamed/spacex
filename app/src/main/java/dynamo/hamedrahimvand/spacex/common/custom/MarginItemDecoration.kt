package dynamo.hamedrahimvand.spacex.common.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dynamo.hamedrahimvand.spacex.common.dpToPx

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
class MarginItemDecoration(
    private val top: Int,
    private val right: Int,
    private val bottom: Int,
    private val left: Int,
    private val orientation: Int = RecyclerView.VERTICAL
) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // Add top margin only for the first item to avoid double space between items
        if (orientation == RecyclerView.VERTICAL) {
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = dpToPx(view.context, top.toFloat()).toInt()
            } else {
                outRect.top = 0
            }
            outRect.left = dpToPx(view.context, left.toFloat()).toInt()
        } else {
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.left = dpToPx(view.context, left.toFloat()).toInt()
            } else {
                outRect.left = 0
            }
            outRect.top = dpToPx(view.context, top.toFloat()).toInt()
        }
        outRect.right = dpToPx(view.context, right.toFloat()).toInt()
        outRect.bottom = dpToPx(view.context, bottom.toFloat()).toInt()
    }

}
package dynamo.hamedrahimvand.spacex.common.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val PAGINATION_COUNT = 10

abstract class ScrollListenerForPagination(
    private var layoutManager: RecyclerView.LayoutManager,
    private var pageCount: Int
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition =
            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
            && firstVisibleItemPosition >= 0
            && totalItemCount >= pageCount
        ) {
            loadMoreData()
        }
    }

    abstract fun loadMoreData()
}

class MyAdapterDataObserver(
    private val onDataChanged: () -> Unit
) : RecyclerView.AdapterDataObserver() {

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(positionStart, itemCount)
        onDataChanged.invoke()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        super.onItemRangeRemoved(positionStart, itemCount)
        onDataChanged.invoke()
    }

    override fun onChanged() {
        super.onChanged()
        onDataChanged.invoke()
    }
}

fun RecyclerView.doPagination(pageCount: Int, onLoadMore: () -> Unit): Boolean {
    if (tag !is Boolean) {
        tag = false
    }
    val dataObserver = MyAdapterDataObserver {
        tag = false
    }
    adapter?.registerAdapterDataObserver(dataObserver)
    return (layoutManager as? LinearLayoutManager)?.run {
        addOnScrollListener(object : ScrollListenerForPagination(this, pageCount) {
            override fun loadMoreData() {
                if (!(tag as Boolean)) {
                    onLoadMore.invoke()
                    tag = true
                }
            }
        })
        true
    } ?: false
}

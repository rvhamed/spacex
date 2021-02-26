package dynamo.hamedrahimvand.spacex.common.custom

import android.animation.Animator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ContentLoadingProgressBar
import dynamo.hamedrahimvand.spacex.R
import dynamo.hamedrahimvand.spacex.common.extensions.hide
import dynamo.hamedrahimvand.spacex.common.extensions.show
import kotlinx.android.synthetic.main.view_full_screen_loading.view.*

class FullScreenLoadingView : FrameLayout {
    private var errorMessage: String? = null

    private var llErrorCenter: LinearLayout? = null
    private var llEmpty: LinearLayout? = null
    private var pgCenter: ContentLoadingProgressBar? = null
    private var parent: FrameLayout? = null
    private var tvErrorBody: TextView? = null
    private var tvRetry: TextView? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.view_full_screen_loading, this)
        setupView()
        setupMargins()
    }

    private fun setupMargins() {
        post {
            val lp = layoutParams as? MarginLayoutParams?
            val margin = lp?.topMargin ?: 0
            if (margin > 0) {

                val lp2 = llErrorCenter?.layoutParams as? LayoutParams
                lp2?.bottomMargin = margin / 2
                llErrorCenter?.layoutParams = lp2


                val lp3 = pgCenter?.layoutParams as? LayoutParams
                lp3?.bottomMargin = margin / 2
                pgCenter?.layoutParams = lp3

                val lp4 = llEmpty?.layoutParams as? LayoutParams
                lp4?.bottomMargin = margin / 2
                llEmpty?.layoutParams = lp4


            }
        }

    }

    private fun setupView() {
        llErrorCenter = findViewById(R.id.llErrorCenter)
        llEmpty = findViewById(R.id.llEmpty)
        pgCenter = findViewById(R.id.pgCenter)
        parent = findViewById(R.id.vLoading)
        tvErrorBody = findViewById(R.id.tvErrorBody)
        tvRetry = findViewById(R.id.tvRetry)
        setState(prevState, prevMessage)
    }


    private fun showLoading() {
        prevState = FullScreenLoadingState.LOADING
        prevMessage = ""
        llErrorCenter?.show()
        llEmpty?.show()
        pgCenter?.show()
        parent?.show()
        show()
    }

    private fun showError(errorMessage: String? = null) {
        prevState = FullScreenLoadingState.ERROR
        prevMessage = errorMessage
        tvErrorBody?.text = (errorMessage ?: this.errorMessage) ?: "".apply {
            if (this.isBlank()) tvErrorBody?.visibility = View.GONE
        }
        parent?.show()
        pgCenter?.hide(false)
        llErrorCenter?.show()
        llEmpty?.hide(false)
        show()
    }

    private fun showEmpty(emptyMessage: String?) {
        prevState = FullScreenLoadingState.EMPTY
        prevMessage = emptyMessage
        llErrorCenter?.hide(false)
        llEmpty?.show()
        pgCenter?.hide()
        parent?.show()
        if (emptyMessage?.isEmpty() == false) {
            llEmpty?.findViewById<TextView>(R.id.tvNoData)?.text = emptyMessage
        }
        show()
    }

    private fun hideAll() {
        prevState = FullScreenLoadingState.DONE
        prevMessage = ""
        llErrorCenter?.hide(false)
        llEmpty?.hide(false)
        pgCenter?.hide(false)
        parent?.hide(false)
        hide(false)
    }

    private fun dismissLoading(translationView: View? = null) {
        translationView?.animate()?.translationY(translationY.toFloat())
            ?.apply {
                interpolator = DecelerateInterpolator()
                duration = 700
            }
        dismissLoadingWithAnimation()
    }

    private fun dismissLoadingWithAnimation() {

        parent?.animate()?.alpha(0f)?.apply {
            setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    parent?.hide(false)
                    parent?.alpha = 1f
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                }
            })
        }?.start()
    }

    var prevState: FullScreenLoadingState = FullScreenLoadingState.LOADING
    var prevMessage: String? = null

    fun setState(
        fullScreenLoadingState: FullScreenLoadingState,
        message: String? = null,
        translationView: View? = null
    ) {
        if (fullScreenLoadingState == prevState) return

        when (fullScreenLoadingState) {
            FullScreenLoadingState.LOADING -> showLoading()
            FullScreenLoadingState.ERROR -> showError(message)
            FullScreenLoadingState.EMPTY -> showEmpty(message)
            FullScreenLoadingState.DONE -> {
                hideAll()
                dismissLoading(translationView)
            }
        }
    }

    fun setNewEmptyParams(
        message: String? = null,
        drawable: Drawable? = null
    ) {
        ivEmpty.setImageDrawable(
            drawable ?: ContextCompat.getDrawable(
                context,
                R.drawable.ic_more
            )
        )
        tvNoData.text = message ?: context.getString(R.string.nothing_found_here)
    }

    fun onRetryClick(onClick: (View) -> Unit) {
        tvRetry?.setOnClickListener {
            showLoading()
            onClick(it)
        }
    }

    override fun setBackground(background: Drawable?) {
        parent?.background = background
    }
}

enum class FullScreenLoadingState {
    LOADING, ERROR, EMPTY, DONE
}

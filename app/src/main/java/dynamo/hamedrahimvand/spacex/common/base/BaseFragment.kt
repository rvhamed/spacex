package dynamo.hamedrahimvand.spacex.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */

abstract class BaseFragment<V : BaseViewModel> : Fragment(), Navigator {

    abstract val viewModel: V
    abstract val viewId: Int
    private var mNavigator: Navigator? = null
    abstract fun setupView()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(viewId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Navigator) {
            mNavigator = context
        }
    }

    override fun navigateTo(navDirections: NavDirections) {
        mNavigator?.navigateTo(navDirections)
    }
}
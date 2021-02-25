package dynamo.hamedrahimvand.spacex.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */

abstract class BaseFragment<V : BaseViewModel> : Fragment() {

    abstract val viewModel: V
    abstract val viewId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(viewId, container, false)
    }
}
package dynamo.hamedrahimvand.spacex.common.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import dynamo.hamedrahimvand.spacex.BuildConfig

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */

abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity(), Navigator {

    abstract val viewModel: V
    abstract val binding: ViewBinding

    @IdRes
    abstract fun navHostId(): Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun navigateTo(navDirections: NavDirections) {
        try {
            navHostId()?.let { id ->
                findNavController(id).navigate(navDirections)
            }
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }

}

package dynamo.hamedrahimvand.spacex.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */

abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity() {

    abstract val viewModel: V
    abstract val binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}

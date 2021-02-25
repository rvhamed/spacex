package dynamo.hamedrahimvand.spacex.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dynamo.hamedrahimvand.spacex.R
import dynamo.hamedrahimvand.spacex.common.base.BaseActivity
import dynamo.hamedrahimvand.spacex.common.extensions.viewBinding
import dynamo.hamedrahimvand.spacex.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    override fun navHostId(): Int = R.id.nav_host_fragment
    override val viewModel: MainViewModel by viewModels()

    override val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
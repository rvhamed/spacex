package dynamo.hamedrahimvand.spacex.ui.main.space

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dynamo.hamedrahimvand.spacex.R
import dynamo.hamedrahimvand.spacex.common.base.BaseFragment
import dynamo.hamedrahimvand.spacex.common.delegates.viewBinding
import dynamo.hamedrahimvand.spacex.common.extensions.log
import dynamo.hamedrahimvand.spacex.databinding.FragmentSpaceListBinding

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
@AndroidEntryPoint
class SpaceListFragment : BaseFragment<SpaceListViewModel>() {
    override val viewModel: SpaceListViewModel by viewModels()
    override val viewId: Int = R.layout.fragment_space_list
    private val binding by viewBinding(FragmentSpaceListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.launchesLiveData.observe(viewLifecycleOwner) { resource ->
            resource.log(resource.toString())
        }
    }

    override fun setupView() {
        binding.btnNext.setOnClickListener {
            viewModel.loadLaunches()
//            navigateTo(SpaceListFragmentDirections.actionSpaceListFragmentToSpaceDetailsFragment(0))
        }
    }
}
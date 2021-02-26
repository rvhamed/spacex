package dynamo.hamedrahimvand.spacex.ui.main.space

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dynamo.hamedrahimvand.spacex.R
import dynamo.hamedrahimvand.spacex.common.base.BaseFragment
import dynamo.hamedrahimvand.spacex.common.custom.FullScreenLoadingState
import dynamo.hamedrahimvand.spacex.common.custom.MarginItemDecoration
import dynamo.hamedrahimvand.spacex.common.delegates.viewBinding
import dynamo.hamedrahimvand.spacex.common.extensions.showSnack
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource.Status.*
import dynamo.hamedrahimvand.spacex.databinding.FragmentSpaceListBinding

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
@AndroidEntryPoint
class SpaceListFragment : BaseFragment<SpaceListViewModel>(), SpaceListCallback {
    override val viewModel: SpaceListViewModel by viewModels()
    override val viewId: Int = R.layout.fragment_space_list
    private val binding by viewBinding(FragmentSpaceListBinding::bind)

    private val spaceListAdapter = SpaceListAdapter(this)

    companion object {
        private const val FULL_SCREEN_LOADING_STATE = "full_screen_loading_state"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        viewModel.launchesLiveData.value?.data?.let { launches ->
            spaceListAdapter.submitList(launches)
        }
    }

    override fun onStop() {
        viewModel.viewState.putSerializable(FULL_SCREEN_LOADING_STATE, binding.fslLoading.prevState)
        super.onStop()
    }

    override fun setupView() {
        (viewModel.viewState.getSerializable(FULL_SCREEN_LOADING_STATE) as? FullScreenLoadingState)?.let { fslState ->
            binding.fslLoading.setState(fslState)
        }


        with(binding.rvLaunches) {
            addItemDecoration(MarginItemDecoration(0, 7, 17, 7))
            adapter = spaceListAdapter
        }
        binding.fslLoading.onRetryClick {
            viewModel.loadLaunches()
        }
    }

    private fun setupViewModel() {
        viewModel.launchesLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                SUCCESS -> {
                    spaceListAdapter.submitList(resource.data)
                    if (resource.data?.isEmpty() == true) {
                        binding.fslLoading.setState(FullScreenLoadingState.EMPTY)
                    } else {
                        binding.fslLoading.setState(FullScreenLoadingState.DONE)
                    }
                }
                ERROR -> {
                    if (spaceListAdapter.currentList.isNullOrEmpty()) {
                        binding.fslLoading.setState(
                            FullScreenLoadingState.ERROR,
                            resource.errorModel?.errorMessage
                                ?: getString(R.string.something_went_wrong)
                        )
                    } else {
                        activity?.showSnack(
                            resource.errorModel?.errorMessage
                                ?: getString(R.string.something_went_wrong)
                        )
                    }
                }
                LOADING -> {
                    binding.fslLoading.setState(FullScreenLoadingState.LOADING)
                }
            }
        }
    }

    override fun onItemClicked(id: String) {
        navigateTo(SpaceListFragmentDirections.actionSpaceListFragmentToSpaceDetailsFragment(id))
    }
}
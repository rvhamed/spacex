package dynamo.hamedrahimvand.spacex.ui.main.space

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dynamo.hamedrahimvand.spacex.BuildConfig
import dynamo.hamedrahimvand.spacex.R
import dynamo.hamedrahimvand.spacex.common.base.BaseFragment
import dynamo.hamedrahimvand.spacex.common.custom.FullScreenLoadingState
import dynamo.hamedrahimvand.spacex.common.custom.MarginItemDecoration
import dynamo.hamedrahimvand.spacex.common.delegates.viewBinding
import dynamo.hamedrahimvand.spacex.common.extensions.*
import dynamo.hamedrahimvand.spacex.data.model.Launch
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource.Status.*
import dynamo.hamedrahimvand.spacex.databinding.FragmentSpaceListBinding

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
@AndroidEntryPoint
class SpaceListFragment : BaseFragment<SpaceListViewModel>(), SpaceListCallback,
    SwipeRefreshLayout.OnRefreshListener {
    override val viewModel: SpaceListViewModel by viewModels()
    override val viewId: Int = R.layout.fragment_space_list
    private val binding by viewBinding(FragmentSpaceListBinding::bind)

    private val spaceListAdapter = SpaceListAdapter(this)

    companion object {
        private const val FULL_SCREEN_LOADING_STATE = "full_screen_loading_state"
        private const val PROGRESS_LOADING_STATE = "progress_loading_state"
        private const val SWIPE_REFRESH_LOADING_STATE = "swipe_refresh_loading_state"
    }

    private var isSnackbarShowing = false
    private val snakbarCallback = object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            super.onDismissed(transientBottomBar, event)
            transientBottomBar?.removeCallback(this)
            isSnackbarShowing = false
        }

        override fun onShown(transientBottomBar: Snackbar?) {
            super.onShown(transientBottomBar)
            isSnackbarShowing = true
        }
    }

    //region View States
    private var fullScreenLoadingState: FullScreenLoadingState?
        set(value) {
            viewModel.viewState.putSerializable(
                FULL_SCREEN_LOADING_STATE,
                value
            )
        }
        get() =
            viewModel.viewState.getSerializable(FULL_SCREEN_LOADING_STATE) as? FullScreenLoadingState

    private var progressLoadingState: Boolean
        set(value) {
            viewModel.viewState.putBoolean(
                PROGRESS_LOADING_STATE,
                value
            )
        }
        get() =
            viewModel.viewState.getBoolean(PROGRESS_LOADING_STATE)


    private var swipeRefreshLoadingState: Boolean
        set(value) {
            viewModel.viewState.putBoolean(
                SWIPE_REFRESH_LOADING_STATE,
                value
            )
        }
        get() =
            viewModel.viewState.getBoolean(SWIPE_REFRESH_LOADING_STATE)
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        viewModel.launchesLiveData.value?.data?.let { launches ->
            spaceListAdapter.submitList(launches)
        }
    }

    override fun onStop() {
        fullScreenLoadingState = binding.fslLoading.prevState
        progressLoadingState = binding.pbLoading.isVisible
        swipeRefreshLoadingState = binding.swipeRefresh.isRefreshing
        super.onStop()
    }

    override fun setupView() {
        fullScreenLoadingState?.let { fslState ->
            binding.fslLoading.setState(fslState)
        }
        if (progressLoadingState) binding.pbLoading.show() else binding.pbLoading.hide(false)

        binding.swipeRefresh.setOnRefreshListener(this)
        with(binding.rvLaunches) {
            addItemDecoration(MarginItemDecoration(0, 7, 17, 7))
            adapter = spaceListAdapter
            doPagination(PAGINATION_COUNT) {
                viewModel.loadNextPage()
            }
        }
        binding.fslLoading.onRetryClick {
            viewModel.loadLaunches(true)
        }

        with(binding.vToolbarList){
            tvTitle.text = getString(R.string.app_name)
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
                    hideAllLoadingViews()
                }
                ERROR -> {
                    if (spaceListAdapter.currentList.isNullOrEmpty()) {
                        binding.fslLoading.setState(
                            FullScreenLoadingState.ERROR,
                            resource.errorModel?.errorMessage
                                ?: getString(R.string.something_went_wrong)
                        )
                    } else {
                        if(!isSnackbarShowing) {
                            activity?.showSnack(
                                resource.errorModel?.errorMessage
                                    ?: getString(R.string.something_went_wrong)
                            )?.addCallback(snakbarCallback)
                        }
                    }
                    hideAllLoadingViews()
                }
                LOADING -> {
                    //For more readability these conditions should be separated.
                    if (spaceListAdapter.currentList.isNullOrEmpty())
                        binding.fslLoading.setState(FullScreenLoadingState.LOADING)

                    if (!resource.data.isNullOrEmpty() && spaceListAdapter.currentList.isNullOrEmpty()) {
                        binding.fslLoading.setState(FullScreenLoadingState.DONE)
                        spaceListAdapter.submitList(resource.data)
                    }

                    if (!spaceListAdapter.currentList.isNullOrEmpty() && !binding.swipeRefresh.isRefreshing)
                        binding.pbLoading.show()

                }
            }
        }
    }

    private fun hideAllLoadingViews() {
        binding.root.post {
            if (binding.pbLoading.isVisible) {
                binding.pbLoading.hide()
            }
            if (binding.swipeRefresh.isRefreshing) {
                binding.swipeRefresh.isRefreshing = false
            }
            binding.rvLaunches.tag = false
        }
    }

    override fun onItemClicked(launch: Launch) {
        navigateTo(SpaceListFragmentDirections.actionSpaceListFragmentToSpaceDetailsFragment(launch))
    }

    override fun onRefresh() {
        binding.swipeRefresh.isRefreshing = true
        viewModel.loadLaunches(true)
    }


}
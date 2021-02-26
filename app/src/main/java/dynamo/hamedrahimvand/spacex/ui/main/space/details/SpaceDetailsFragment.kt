package dynamo.hamedrahimvand.spacex.ui.main.space.details

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dynamo.hamedrahimvand.spacex.R
import dynamo.hamedrahimvand.spacex.common.base.BaseFragment
import dynamo.hamedrahimvand.spacex.common.delegates.viewBinding
import dynamo.hamedrahimvand.spacex.databinding.FragmentSpaceDetailsBinding

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
@AndroidEntryPoint
class SpaceDetailsFragment : BaseFragment<SpaceDetailsViewModel>() {
    override val viewModel: SpaceDetailsViewModel by viewModels()
    override val viewId: Int = R.layout.fragment_space_details
    private val args by navArgs<SpaceDetailsFragmentArgs>()
    private val binding by viewBinding(FragmentSpaceDetailsBinding::bind)

    override fun setupView() {
        binding.textView.text = "id: ${args.spaceId}"
    }
}
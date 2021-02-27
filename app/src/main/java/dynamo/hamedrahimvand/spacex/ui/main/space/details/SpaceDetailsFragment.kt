package dynamo.hamedrahimvand.spacex.ui.main.space.details

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dynamo.hamedrahimvand.spacex.R
import dynamo.hamedrahimvand.spacex.common.base.BaseFragment
import dynamo.hamedrahimvand.spacex.common.delegates.viewBinding
import dynamo.hamedrahimvand.spacex.common.extensions.DAY_DATE_PATTERN
import dynamo.hamedrahimvand.spacex.common.extensions.loadUrl
import dynamo.hamedrahimvand.spacex.common.extensions.show
import dynamo.hamedrahimvand.spacex.common.extensions.toFormattedStringUTC
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
        binding.ivLarge.loadUrl(
            args.launch.links?.patch?.small,
            R.drawable.ic_rocket_placeholder,
            R.drawable.ic_rocket_placeholder
        )
        binding.tvDetails.text = args.launch.details
        binding.tvName.text = args.launch.name
        binding.tvDate.text = args.launch.date.toFormattedStringUTC(DAY_DATE_PATTERN)

        with(binding.vToolbar) {
            tvTitle.text = getString(R.string.app_name)
            ibBack.setOnClickListener {
                popBackStack()
            }
            ibBack.show()
        }
    }
}
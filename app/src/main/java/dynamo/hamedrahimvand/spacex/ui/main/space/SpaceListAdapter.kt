package dynamo.hamedrahimvand.spacex.ui.main.space

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dynamo.hamedrahimvand.spacex.R
import dynamo.hamedrahimvand.spacex.common.extensions.loadUrl
import dynamo.hamedrahimvand.spacex.data.model.ui_models.LaunchItem
import dynamo.hamedrahimvand.spacex.databinding.ItemLaunchesBinding

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
class SpaceListAdapter(val spaceListCallback: SpaceListCallback) :
    ListAdapter<LaunchItem, SpaceListAdapter.SpaceListViewHolder>(SELECTION_COMPARATOR) {

    companion object {
        private val SELECTION_COMPARATOR = object : DiffUtil.ItemCallback<LaunchItem>() {
            override fun areItemsTheSame(
                oldItem: LaunchItem,
                newItem: LaunchItem
            ): Boolean = oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: LaunchItem,
                newItem: LaunchItem
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceListViewHolder {
        val itemBinding =
            ItemLaunchesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpaceListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SpaceListViewHolder, position: Int) {
        holder.bind()
    }

    inner class SpaceListViewHolder(private val itemBinding: ItemLaunchesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.cvRoot.setOnClickListener {
                spaceListCallback.onItemClicked(currentList[adapterPosition].id)
            }
        }

        fun bind() {
            val launch = currentList[adapterPosition]
            with(itemBinding) {
                tvName.text = launch.name
                ivAvatar.loadUrl(
                    launch.smallIcon,
                    R.drawable.ic_rocket_placeholder,
                    R.drawable.ic_rocket_placeholder
                )
            }
        }
    }
}

interface SpaceListCallback {
    fun onItemClicked(id: String)
}
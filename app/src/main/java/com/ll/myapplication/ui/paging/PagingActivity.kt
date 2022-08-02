package com.ll.myapplication.ui.paging

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ll.myapplication.data.db.Users
import com.ll.myapplication.databinding.ActivityPagingBinding
import com.ll.myapplication.databinding.ItemPagingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PagingActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPagingBinding.inflate(layoutInflater) }

    private val vm: PagingVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = UserAdapter(UserAdapter.UserComparator)
        binding.rvData.adapter = adapter
        lifecycleScope.launch {
            vm.flow2.collect {
                adapter.submitData(it)
            }
        }
    }

    class UserAdapter(diffCallback: DiffUtil.ItemCallback<Users>) :
        PagingDataAdapter<Users, UserViewHolder>(diffCallback) {

        object UserComparator : DiffUtil.ItemCallback<Users>() {
            override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
                // Id is unique.
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
                return oldItem == newItem
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): UserViewHolder {
            return UserViewHolder(ItemPagingBinding.inflate(LayoutInflater.from(parent.context)))
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val item = getItem(position)
            // Note that item may be null. ViewHolder must support binding a
            // null item as a placeholder.
            if (item != null) {
                holder.bind(item)
            }
        }
    }

    class UserViewHolder(private val holderBinding: ItemPagingBinding) :
        RecyclerView.ViewHolder(holderBinding.root) {
        fun bind(item: Users) {
            holderBinding.root.text = "id:${item.id},text=${item.label}"
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, PagingActivity::class.java)
            context.startActivity(starter)
        }
    }
}
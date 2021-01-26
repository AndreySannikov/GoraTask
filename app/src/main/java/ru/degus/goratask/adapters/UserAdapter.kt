package ru.degus.goratask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.degus.goratask.App
import ru.degus.goratask.R
import ru.degus.goratask.components.DiffCallback
import ru.degus.goratask.databinding.UserItemViewBinding
import ru.degus.goratask.models.UsersItem

class UserAdapter(var onUserClickListener: OnUserClickListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var items: List<UsersItem> = ArrayList()


    interface OnUserClickListener {   //интерфейс слушатель для клика по элементу
        fun onUserClick(id: Int)
    }

    fun setItems(newItems: List<UsersItem>) {   // установка нового списка в Adapter
        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, newItems), false)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {  // создание ViewHolder
        val li = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<UserItemViewBinding>(
                li,
                R.layout.user_item_view,
                parent,
                false
        )

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.setOnClickListener {                                        //обработка клика на элемент списка
            items[position].id.let { it -> onUserClickListener.onUserClick(it) }
        }
        holder.bind(items[position])

    }

    override fun getItemCount(): Int = items.size

    class UserViewHolder(private val binding: UserItemViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: UsersItem){
            binding.tvName.text = item.name                   //установка имени пользователя
        }
    }
}
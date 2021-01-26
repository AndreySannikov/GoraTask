package ru.degus.goratask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ru.degus.goratask.R
import ru.degus.goratask.databinding.PhotoItemViewBinding
import ru.degus.goratask.models.PhotosItem

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private var items: List<PhotosItem> = ArrayList()


    fun setItems(items: List<PhotosItem>) { // установка нового списка в Adapter
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder { // создание ViewHolder
        val li = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<PhotoItemViewBinding>(
            li,
            R.layout.photo_item_view,
            parent,
            false
        )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class PhotoViewHolder(private val binding: PhotoItemViewBinding) : RecyclerView.ViewHolder(
        binding.root
    ){

        fun bind(item: PhotosItem) {
            binding.tvTitle.text = item.title                      //установка заголовка фотографии
            binding.pbLoadingPhoto.visibility = View.VISIBLE       //включение прогресс бара
            Picasso.get()                                           //загрузка и кэширование фотографии
                .load(item.url)
                .fit()
                .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                .into(binding.ivPhoto, object : Callback {
                    override fun onSuccess() {
                        binding.pbLoadingPhoto.visibility = View.GONE   //отключение прогресс бара при успешной загрузке
                    }

                    override fun onError(e: Exception?) {

                    }
                })
        }
    }
}
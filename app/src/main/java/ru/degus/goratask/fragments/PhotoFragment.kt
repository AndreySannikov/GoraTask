package ru.degus.goratask.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ru.degus.goratask.App
import ru.degus.goratask.MainActivity
import ru.degus.goratask.R
import ru.degus.goratask.adapters.PhotoAdapter
import ru.degus.goratask.databinding.PhotoFragmentBinding
import ru.degus.goratask.models.PhotosItem
import ru.degus.goratask.viewmodels.PhotoViewModel
import javax.inject.Inject

class PhotoFragment : BaseFragment<PhotoFragmentBinding>(R.layout.photo_fragment) {
    override fun injectDagger() {
        App.instance.getAppComponent()
            .activitySubComponentBuilder()
            .with(navigator as FragmentActivity)
            .build()
            .inject(this)

        App.instance.getViewModelSubComponent().inject(viewModel)
    }

    @Inject
    lateinit var viewModel: PhotoViewModel                                          //инъекция PhotoViewModel

    lateinit var adapter: PhotoAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createRecyclerView()                                                        //создания recyclerView с переферией
//        binding.viewModel = viewModel                                               //привязка viewModel

        viewModel.errorData.observe(viewLifecycleOwner,                             //подписка на изменение errorData
            Observer {
                showInformDialog("Error", it.message) {
                    navigator.closeApp()                                            //закрытия приложения, если возникает ошибка в получении запроса
                }
            }
        )

        viewModel.photosData.observe(viewLifecycleOwner,                   //подписка на изменение photosData
            Observer {                 //получение нового списка
                    setItems(it)
            }
        )

        val album = arguments?.getString("album")                      //получение строки из UserFragment
        Log.d("onArgument", album.toString())
        album?.let {
            if ((navigator as MainActivity).isConnected()) {                       //проверка на наличие доступа к сети
                viewModel.loadPhotos(it)
            } else navigator.toast("No internet connection")
        }

    }

    private fun setItems(photos: List<PhotosItem>) {                                    //метод в котором осуществляется привязка
        adapter.setItems(photos)
    }

    private fun createRecyclerView() {                                      //создание recyclerView
        val llm = LinearLayoutManager(navigator as Context)
        binding.rvDownloadPhotos.layoutManager = llm

        adapter = PhotoAdapter()
        binding.rvDownloadPhotos.adapter = adapter
    }
}
package ru.degus.goratask.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.degus.goratask.App
import ru.degus.goratask.Layout
import ru.degus.goratask.MainActivity
import ru.degus.goratask.R
import ru.degus.goratask.adapters.UserAdapter
import ru.degus.goratask.databinding.UserFragmentBinding
import ru.degus.goratask.models.UsersItem
import ru.degus.goratask.viewmodels.UserViewModel
import javax.inject.Inject

class UserFragment : BaseFragment<UserFragmentBinding>(R.layout.user_fragment) {
    override fun injectDagger() {
        App.instance.getAppComponent()
                .activitySubComponentBuilder()
                .with(navigator as FragmentActivity)
                .build()
                .inject(this)

        App.instance.getViewModelSubComponent().inject(viewModel)
    }

    @Inject
    lateinit var viewModel: UserViewModel                                           //инъекция UserViewModel

    lateinit var adapter: UserAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        createRecyclerView()                                                    //создания recyclerView с переферией

        observeViewModel()                                                      //подписка на изменения состаяния viewModel


        if ((navigator as MainActivity).isConnected()) {                       //проверка на наличие доступа к сети
            viewModel.downloadUsers()
        } else navigator.toast("No internet connection")
    }

    private fun observeViewModel() {
        viewModel.errorData.observe(viewLifecycleOwner,
                Observer {
                    showInformDialog("Error", it.message) {
                        navigator.closeApp()                                        //закрытия приложения при ошибке в получении запроса
                    }
                }
        )

        viewModel.usersData.observe(viewLifecycleOwner,
                Observer {
                    setItems(it)                                //установка нового списка в recyclerView при удачной загрузке списка альбомов
                }
        )
    }

    private fun setItems(users: List<UsersItem>) {
        adapter.setItems(users)                               //установка нового списка
    }

    private fun createRecyclerView() {                                          //создание recyclerView
        val llm = LinearLayoutManager(navigator as Context)
        binding.rvDownloadUsers.layoutManager = llm

        val onAlbumClickListener: UserAdapter.OnUserClickListener = object : UserAdapter.OnUserClickListener { // реализация интерфейса слушателя клика по элементу списка
            override fun onUserClick(id: Int) {
                Log.d("onClick", id.toString())
                navigator.navigateTo(Layout.PHOTO, "album" to id.toString())            //переход в PhotoFragment и передача в него индификатора альбома с фотографиями пользователя
            }

        }

        adapter = UserAdapter(onAlbumClickListener)
        binding.rvDownloadUsers.adapter = adapter
        binding.rvDownloadUsers.addItemDecoration(DividerItemDecoration(navigator as Context, LinearLayoutManager.VERTICAL))
    }
}
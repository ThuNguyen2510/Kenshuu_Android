package com.example.kenshuu.di
import com.example.kenshuu.ui.login.LoginViewModel
import com.example.kenshuu.ui.main.MainViewModel
import com.example.kenshuu.ui.slider.SliderViewModel
import com.example.kenshuu.ui.total.TotalViewModel
import com.example.kenshuu.ui.user.create.CreateUserViewModel
import com.example.kenshuu.ui.user.delete.DeleteUserActivity
import com.example.kenshuu.ui.user.delete.DeleteUserViewModel
import com.example.kenshuu.ui.user.update.UpdateUserViewModel
import com.example.kenshuu.utils.PrefsManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
@JvmField
val appModule = module{
    //view model
    viewModel { LoginViewModel(get()) }
    viewModel { SliderViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { TotalViewModel(get()) }
    viewModel { CreateUserViewModel(get()) }
    viewModel { DeleteUserViewModel(get()) }
    viewModel { UpdateUserViewModel(get()) }
    // singleton
    single { PrefsManager() }
}
// app module
val App = listOf(appModule, remoteDataSourceModule)
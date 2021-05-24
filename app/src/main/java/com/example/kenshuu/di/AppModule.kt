package com.example.kenshuu.di
import com.example.kenshuu.ui.login.LoginViewModel
import com.example.kenshuu.utils.PrefsManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
@JvmField
val appModule = module{
    viewModel { LoginViewModel(get()) }
    // singleton
    single { PrefsManager() }
}
// Gather all app modules
val App = listOf(appModule, remoteDataSourceModule)
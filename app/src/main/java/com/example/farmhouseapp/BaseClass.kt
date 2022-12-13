package com.example.farmhouseapp

import android.app.Application
import com.example.farmhouseapp.repositories.MainRepository
import com.example.farmhouseapp.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BaseClass():Application() {
    private val appModule = module{
        single { MainRepository(get()) }
        //   single { AdsRepository(this@MainApplication) }
        viewModel { MainViewModel(get()) }
    }


    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@BaseClass)
            modules(listOf(appModule))
        }
    }


}



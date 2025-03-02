package com.example.aroundegyptmini

import android.app.Application
import com.example.aroundegyptmini.data.AppContainer
import com.example.aroundegyptmini.data.DefaultAppContainer

class AroundEgyptMiniApplication : Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
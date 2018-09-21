package com.gmerwan.mobiledevdeliveryapp

import android.app.Application
import com.gmerwan.mobiledevdeliveryapp.roomdatabase.RoomService


class App: Application(){
    override fun onCreate() {
        super.onCreate()
        RoomService.context = applicationContext
    }
}
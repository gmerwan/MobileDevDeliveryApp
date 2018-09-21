package com.gmerwan.mobiledevdeliveryapp.roomdatabase

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.gmerwan.mobiledevdeliveryapp.entity.Delivery
import com.gmerwan.mobiledevdeliveryapp.roomdao.DeliveryDao

@Database(entities = arrayOf(Delivery::class),version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getDeliveryDao():DeliveryDao

}
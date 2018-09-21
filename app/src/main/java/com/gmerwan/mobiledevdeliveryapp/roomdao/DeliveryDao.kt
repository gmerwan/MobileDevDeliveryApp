package com.gmerwan.mobiledevdeliveryapp.roomdao

import android.arch.persistence.room.*
import com.gmerwan.mobiledevdeliveryapp.entity.Delivery

@Dao
interface DeliveryDao {

    @Query("select * from deliveries")
    fun getDeliveries():List<Delivery>

    @Query("select * from deliveries where delivery_id=:idDelivery")
    fun getDeliveryById(idDelivery:Int):Delivery

    @Insert
    fun addDeliveries(deliveries:List<Delivery>)

    @Update
    fun updateDelivery(Delivery: Delivery)

}
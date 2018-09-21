package com.gmerwan.mobiledevdeliveryapp.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "deliveries")
data class Delivery(
        @PrimaryKey
        @ColumnInfo(name="delivery_id")
        var idDelivery:Int,

        @ColumnInfo(name="description")
        var description:String="",

        @ColumnInfo(name="image_url")
        var urlImage:String="",

        @ColumnInfo(name="location_lat")
        var latitude:String="",

        @ColumnInfo(name="location_lng")
        var longitude:String="",

        @ColumnInfo(name="location_address")
        var address:String=""):Serializable
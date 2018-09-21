package com.gmerwan.mobiledevdeliveryapp.ui

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gmerwan.mobiledevdeliveryapp.R
import com.gmerwan.mobiledevdeliveryapp.entity.Delivery
import com.gmerwan.mobiledevdeliveryapp.viewmodel.DeliveryModel

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val deliveryModel = ViewModelProviders.of(this).get(DeliveryModel::class.java)
        val delivery = intent.getSerializableExtra("delivery") as Delivery
        if (deliveryModel.delivery!=null) {
            deliveryModel.displayDetail(this, deliveryModel.delivery!!)
        }
        else {
            deliveryModel.loadDetail(this, delivery)
        }
    }
}

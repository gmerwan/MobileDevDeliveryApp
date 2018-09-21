package com.gmerwan.mobiledevdeliveryapp.ui

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gmerwan.mobiledevdeliveryapp.R
import com.gmerwan.mobiledevdeliveryapp.adapter.DeliveryAdapter
import com.gmerwan.mobiledevdeliveryapp.entity.Delivery
import com.gmerwan.mobiledevdeliveryapp.viewmodel.DeliveryModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.intentFor


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // View Model instance
        val deliveryModel = ViewModelProviders.of(this).get(DeliveryModel::class.java)
        // If the list of cities is null, load the list from DB
        if (deliveryModel.deliveries==null) {
            deliveryModel.loadData(this)
        }
        else {
            // After the rotation of the screen, use cities of the ViewModel instance

            listdeliveries.adapter = DeliveryAdapter(this, deliveryModel.deliveries!!)
        }

        // Display detail data if width >= 600 dp

        if (isTwoPane() && deliveryModel.delivery!=null) {
            deliveryModel.displayDetail(this,deliveryModel.delivery!!)

        }

        // ListView OnClick Item

        listdeliveries.setOnItemClickListener { adapterView, view, i, l ->
            val delivery = (adapterView.getItemAtPosition(i) as Delivery)
            if (isTwoPane()) {
                // display detail data
                deliveryModel.loadDetail(this,delivery)
            }
            else {
                startActivity(intentFor<DetailActivity>("delivery" to delivery))
            }
        }
    }

    private fun isTwoPane() = findViewById<View>(R.id.fragment2) != null



}

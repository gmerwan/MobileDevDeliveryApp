package com.gmerwan.mobiledevdeliveryapp.viewmodel

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gmerwan.mobiledevdeliveryapp.entity.Delivery
import com.gmerwan.mobiledevdeliveryapp.R
import com.gmerwan.mobiledevdeliveryapp.adapter.DeliveryAdapter
import com.gmerwan.mobiledevdeliveryapp.retrofit.RetrofitService
import com.gmerwan.mobiledevdeliveryapp.roomdatabase.RoomService
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.TargetApi
import android.os.Build


class DeliveryModel:ViewModel() {


    private lateinit var googleMap: GoogleMap
    var delivery: Delivery? = null
    var deliveries:List<Delivery>? = null


    fun loadData(act:Activity) {
        act.progressBar1.visibility = View.VISIBLE
        // Get deliveries from SQLite DB
        deliveries = RoomService.appDataBase.getDeliveryDao().getDeliveries()

        if (deliveries?.size == 0) {
            // If the list of deliveries is empty, load from server and save them in SQLite DB
            getDeliveriesFromRemote(act)
        }
        else {
            act.progressBar1.visibility = View.GONE
            act.listdeliveries.adapter = DeliveryAdapter(act, deliveries!!)
        }




    }

    private fun getDeliveriesFromRemote(act:Activity) {
        val call = RetrofitService.endpoint.getDeliveries()
        call.enqueue(object : Callback<List<Delivery>> {
            override fun onResponse(call: Call<List<Delivery>>?, response: Response<List<Delivery>>?) {
                act.progressBar1.visibility = View.GONE
                if (response?.isSuccessful!!) {
                    deliveries = response.body()
                    act.progressBar1.visibility = View.GONE
                    act.listdeliveries.adapter = DeliveryAdapter(act, deliveries!!)
                    // save deliveries in SQLite DB
                    RoomService.appDataBase.getDeliveryDao().addDeliveries(deliveries!!)
                } else {
                    act.toast("Une erreur s'est produite")
                }
            }

            override fun onFailure(call: Call<List<Delivery>>?, t: Throwable?) {
                act.progressBar1.visibility = View.GONE
                act.toast("Une erreur s'est produite")
            }


        })
    }

    fun loadDetail(act:Activity,delivery:Delivery) {
        act.progressBar2.visibility = View.VISIBLE
        // load delivery detail from SQLite DB
        this.delivery = RoomService.appDataBase.getDeliveryDao().getDeliveryById(delivery.idDelivery)
        act.progressBar2.visibility = View.GONE
        displayDetail(act, this.delivery!!)

    }

    @TargetApi(Build.VERSION_CODES.M)
    fun displayDetail(act: Activity, delivery: Delivery) {
        Glide.with(act).load(delivery.urlImage)
                .apply(RequestOptions().placeholder(R.drawable.place_holder))
                .into(act.item_detail_image)

        val description = delivery.description + " at " + delivery.address
        act.item_detail_text.text = description
        act.mapView.onResume()
        try {
            MapsInitializer.initialize(act.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        act.mapView.getMapAsync(OnMapReadyCallback { mMap ->
            googleMap = mMap

            // For showing a move to my location button
            // Here we should ask for permission
            //googleMap.isMyLocationEnabled = true

            // For dropping a marker at a point on the Map
            val location = LatLng(delivery.latitude.toDouble(), delivery.longitude.toDouble())
            googleMap.addMarker(MarkerOptions().position(location).title(delivery.address).snippet(delivery.description))

            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(location).zoom(12f).build()
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        })

    }
}

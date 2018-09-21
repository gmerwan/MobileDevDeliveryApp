package com.gmerwan.mobiledevdeliveryapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gmerwan.mobiledevdeliveryapp.R
import com.gmerwan.mobiledevdeliveryapp.baseUrl
import com.gmerwan.mobiledevdeliveryapp.entity.Delivery


class DeliveryAdapter(_ctx:Context, _listDeliveries:List<Delivery>):BaseAdapter() {
    private var ctx = _ctx
    private val listDeliveries = _listDeliveries


    override fun getItem(p0: Int) = listDeliveries[p0]

    override fun getItemId(p0: Int) = listDeliveries[p0].hashCode().toLong()

    override fun getCount()= listDeliveries.size

    override fun getView(position: Int, p0: View?, parent: ViewGroup?): View {

        var view = p0
        val viewHolder: ViewHolder
        if(view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.delivery_layout,parent,false)
            val image = view?.findViewById(R.id.item_image) as ImageView
            val text = view.findViewById(R.id.item_text) as TextView
            viewHolder= ViewHolder(image, text)
            view.tag = viewHolder
        }
        else {
            viewHolder = view.tag as ViewHolder

        }
        Glide.with(ctx).load(baseUrl + listDeliveries[position].urlImage)
                .apply(RequestOptions().placeholder(R.drawable.place_holder))
                .into(viewHolder.image)

        val description = listDeliveries[position].description + " at " + listDeliveries[position].address
        viewHolder.text.text = description
        return view

    }

    private data class ViewHolder(var image:ImageView, var text:TextView)
}
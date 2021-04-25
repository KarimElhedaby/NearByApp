package com.example.nearbyapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nearbyapp.R
import com.nearby.app.data.ui_models.Item
import com.nearby.app.utils.loadImage
import kotlinx.android.synthetic.main.item_place.view.*
import java.lang.StringBuilder


class PlacesAdapter(
    val data: MutableList<Item>,
    private val listener: PlaceListener
) : RecyclerView.Adapter<PlacesAdapter.PlacesAdapterViewHolder>() {

    inner class PlacesAdapterViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener.onClickPlace(data[adapterPosition])
            }
        }


        fun bind(item: Item) = with(itemView) {

//            val placeImage = item.photo.let {
//                "${it.prefix}${it.width}x${it.height}${it.suffix}"
//            }
//
//            placeIV.loadImage(placeImage)

            placeNameTV.text = item.venue.name


            // handle description concatenation
            val placeDescription = StringBuilder()

            for (location in item.venue.location.formattedAddress) {
                placeDescription.append(location + "\n")
            }

            placeDescriptionTV.text = placeDescription

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlacesAdapterViewHolder {
        return PlacesAdapterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_place, null)
        )
    }

    override fun onBindViewHolder(holder: PlacesAdapterViewHolder, position: Int) =
        holder.bind(data[position])


    fun addMultipleItemsData(receivedData: MutableList<Item>) {
        data.addAll(receivedData)
        notifyDataSetChanged()
    }

    fun swapData(receivedData: MutableList<Item>) {
        data.clear()
        data.addAll(receivedData)
        notifyDataSetChanged()
    }

    fun addSingleItemDataFromLast(receivedData: Item) {
        data.add(receivedData)
        notifyItemInserted(data.size)
    }

    fun removeAllData() {
        data.clear()
        notifyDataSetChanged()
    }

    interface PlaceListener {
        fun onClickPlace(place: Item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}


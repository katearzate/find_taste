package com.example.findtaste.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findtaste.R
import com.example.findtaste.models.Commerce
import com.example.findtaste.models.Menu
import com.squareup.picasso.Picasso

class RecyclerFavoriteCommerces(var context: Context, var favorites: List<Commerce>):
    RecyclerView.Adapter<RecyclerFavoriteCommerces.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameCommerce: TextView = itemView.findViewById(R.id.mapTypeName)
        val address: TextView = itemView.findViewById(R.id.mapTypeAddress)
        val description: TextView = itemView.findViewById(R.id.mapTypeDescription)
        val image: ImageView = itemView.findViewById(R.id.mapTypeImage)
        val layout: LinearLayout = itemView.findViewById(R.id.recyclerFavorite)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_map_food_type,
            parent,
            false
        )
        return ViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var commerce: Commerce = favorites.get(position)

        holder.nameCommerce.setText(commerce.commerce)
        holder.address.setText(commerce.address)
        holder.description.setText(commerce.description)

        Picasso.get().load(commerce.photo).into(holder.image)
    }

    override fun getItemCount(): Int = favorites.size
}
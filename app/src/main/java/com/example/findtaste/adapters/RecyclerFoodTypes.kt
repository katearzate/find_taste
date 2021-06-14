package com.example.findtaste.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findtaste.R
import com.example.findtaste.models.Menu

class RecyclerFoodTypes (var context: Context, var menus: List<Menu>):
    RecyclerView.Adapter<RecyclerFoodTypes.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.recyclerFoodImage)
        val type : TextView = itemView.findViewById(R.id.recyclerFoodType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_food_types,
            parent,
            false
        )
        return ViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var menu : Menu = menus.get(position)

        holder.image.setImageResource(menu.image)
        holder.type.setText(menu.foodType)
    }

    override fun getItemCount(): Int = menus.size
}

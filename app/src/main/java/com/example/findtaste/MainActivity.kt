package com.example.findtaste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findtaste.adapters.RecyclerFoodTypes
import com.example.findtaste.databinding.ActivityMainBinding
import com.example.findtaste.models.Menu

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val menus: MutableList<Menu> = arrayListOf()
        menus.add(Menu(R.drawable.taco, "Mexicana"))
        menus.add(Menu(R.drawable.burger, "Estadounidense"))
        menus.add(Menu(R.drawable.pizza, "Comida rápida"))
        menus.add(Menu(R.drawable.dish, "Otra"))
        menus.add(Menu(R.drawable.sushi, "Japonesa"))
        menus.add(Menu(R.drawable.china, "China"))
        menus.add(Menu(R.drawable.pasta, "Italiana"))
        menus.add(Menu(R.drawable.salad, "Vegetariana"))
        menus.add(Menu(R.drawable.dessert, "Postres"))
        menus.add(Menu(R.drawable.cafe, "Cafe"))

        binding.recyclerFoodTypes.layoutManager = GridLayoutManager(
            this,
            2,
            RecyclerView.VERTICAL,
            false
        )
        binding.recyclerFoodTypes.adapter = RecyclerFoodTypes(this, menus)

    }
}
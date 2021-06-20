package com.example.findtaste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
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

        var menus: MutableList<Menu> = arrayListOf()
        menus.add(Menu(R.drawable.mexicana, "Mexicana"))
        menus.add(Menu(R.drawable.japonesa, "Japonesa"))
        menus.add(Menu(R.drawable.postres, "Postres"))
        menus.add(Menu(R.drawable.cafe, "Cafe"))

        binding.recyclerFoodTypes.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerFoodTypes.adapter = RecyclerFoodTypes(this, menus)



        /*
        recyclerView.layoutManager = GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
        recyclerView.adapter = object : AdapterEleccionItems(context, materias){
            override fun clickClaseItem(materia: Materia) {
                clickClaseSeleccionada(materia)
            }
        }
         */
    }
}
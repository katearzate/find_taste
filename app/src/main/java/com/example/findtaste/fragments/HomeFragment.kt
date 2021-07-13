package com.example.findtaste.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findtaste.MapsActivity
import com.example.findtaste.R
import com.example.findtaste.adapters.RecyclerFoodTypes
import com.example.findtaste.databinding.FragmentHomeBinding
import com.example.findtaste.databinding.FragmentSearchBinding
import com.example.findtaste.models.HomeViewModel
import com.example.findtaste.models.Menu

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private var lat = 0.0
    private var lng = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        homeViewModel.getLat()?.observe(viewLifecycleOwner, { l ->
            l?.let {
                lat = it
                println("Lat received: $it")
            }
        })

        homeViewModel.getLng()?.observe(viewLifecycleOwner, { l ->
            l?.let {
                lng = it
                println("Lat received: $it")
            }
        })

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
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false
        )

        binding.recyclerFoodTypes.adapter = object: RecyclerFoodTypes(requireContext(), menus){
            override fun foodType(type: String) {
                val intent = Intent(requireContext(), MapsActivity::class.java)
                intent.putExtra("type", type)
                intent.putExtra("lat", lat)
                intent.putExtra("lng", lng)
                startActivity(intent)
            }

        }


        return binding.root
    }

}
package com.example.findtaste.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findtaste.R
import com.example.findtaste.adapters.RecyclerFoodTypes
import com.example.findtaste.databinding.FragmentSearchBinding
import com.example.findtaste.models.Menu

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater)

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
                //TODO: pasar nombre de la categoria elegida al sig fragmento
                findNavController().navigate(R.id.action_searchFragment_to_searchTypeFragment)
            }

        }

        return binding.root
    }

}
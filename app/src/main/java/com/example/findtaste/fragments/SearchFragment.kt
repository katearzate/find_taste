package com.example.findtaste.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findtaste.R
import com.example.findtaste.adapters.RecyclerFoodTypes
import com.example.findtaste.databinding.FragmentSearchBinding
import com.example.findtaste.models.Menu
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

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
        binding.recyclerFoodTypes.layoutManager = GridLayoutManager(
                requireContext(),
                2,
                RecyclerView.VERTICAL,
                false
            )
/*
        binding.recyclerFoodTypes.adapter = object: RecyclerFoodTypes(requireContext(), menus){
            override fun foodType(type: String) {
                //TODO: pasar nombre de la categoria elegida al sig fragmento
                findNavController().navigate(R.id.action_searchFragment_to_searchTypeFragment)
            }

        }


 */
        return binding.root
    }

}
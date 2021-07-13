package com.example.findtaste.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findtaste.MapsActivity
import com.example.findtaste.adapters.RecyclerFoodTypes
import com.example.findtaste.databinding.FragmentHomeBinding
import com.example.findtaste.models.HomeViewModel
import com.example.findtaste.models.Menu
import com.example.findtaste.models.Tools.Companion.toast
import com.google.firebase.firestore.FirebaseFirestore

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

        val db = FirebaseFirestore.getInstance()

        val menus: MutableList<Menu> = arrayListOf()
        val types: ArrayList<String> = arrayListOf("Estadounidense", "Mexicana", "Comida rápida",
            "Otra", "Japonesa", "China", "Italiana", "Vegetariana", "Postres", "Cafe")

        val docRef = db.collection("MyDB").document("FirebaseData")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (type in types){
                        menus.add(Menu(document.getString(type)!!, type))
                    }

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
                }
            }
            .addOnFailureListener { exception ->
                "Error: $exception".toast(requireContext())
            }

        return binding.root
    }

}
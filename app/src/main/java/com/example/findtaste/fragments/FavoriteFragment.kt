package com.example.findtaste.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findtaste.R
import com.example.findtaste.adapters.RecyclerFavoriteCommerces
import com.example.findtaste.databinding.FragmentFavoriteBinding
import com.example.findtaste.models.Commerce
import com.example.findtaste.models.DBManager

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private var _dbManager: DBManager? = null
    private val dbManager get() = _dbManager!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        _dbManager = DBManager(requireContext(), "favCommerces", null, 1)

        val favorites: List<Commerce> = dbManager.showFavorites()

        if (favorites.isEmpty()){
            binding.recyclerFavoriteCommerces.visibility = View.INVISIBLE
            binding.noFavoriteCommerces.visibility = View.VISIBLE
        }else{
            binding.recyclerFavoriteCommerces.visibility = View.VISIBLE
            binding.noFavoriteCommerces.visibility = View.GONE

            binding.recyclerFavoriteCommerces.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerFavoriteCommerces.adapter = RecyclerFavoriteCommerces(
                requireContext(),
                favorites
            )
        }

        return binding.root
    }

}
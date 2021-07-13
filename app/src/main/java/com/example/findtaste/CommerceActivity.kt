package com.example.findtaste

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.findtaste.databinding.ActivityCommerceBinding
import com.example.findtaste.models.Commerce
import com.example.findtaste.models.DBManager
import com.example.findtaste.models.Tools.Companion.toast
import com.squareup.picasso.Picasso

class CommerceActivity : AppCompatActivity() {

    private var _binding: ActivityCommerceBinding? = null
    private val binding get() = _binding!!
    private var _dbManager: DBManager? = null
    private val dbManager get() = _dbManager!!

    private lateinit var commerce: Commerce
    private var pressedFav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCommerceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        _dbManager = DBManager(this, "favCommerces", null, 1)

        commerce = intent.getSerializableExtra("commerce") as Commerce

        Picasso.get().load(commerce.photo).into(binding.commerceImage)
        binding.commerceName.setText(commerce.commerce)
        binding.commerceCategory.setText("Categoria: ${commerce.category}")
        binding.commerceDescription.setText(commerce.description)
        binding.commerceAddress.setText(commerce.address)
        binding.commerceLatLng.setText("(${commerce.lat}, ${commerce.lng})")
        binding.commerceTelephone.setText(commerce.telephone)

        binding.commerceBtnFavorite.setOnClickListener {
            pressedFav = !pressedFav
            if (pressedFav){
                binding.commerceBtnFavorite.setIconResource(android.R.drawable.btn_star_big_on)
                dbManager.addCommerce(commerce)
                "Agregado a favoritos".toast(this)
            }else{
                binding.commerceBtnFavorite.setIconResource(android.R.drawable.star_off)
                dbManager.removeFav(commerce)
                "Eliminado de favoritos".toast(this)
            }
        }

    }
}
package com.example.findtaste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.findtaste.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navigationController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        navigationController = findNavController(R.id.main_container)
        setupActionBarWithNavController(
            navigationController, AppBarConfiguration(
                setOf(
                    R.id.homeFragment,
                    R.id.searchFragment,
                    R.id.favoriteFragment,
                    R.id.accountFragment
                )
            )
        )

        binding.menuBottomNavigation.setupWithNavController(navigationController)
        binding.menuBottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menuHome -> {
                    navigationController.navigate(R.id.homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menuSearch -> {
                    navigationController.navigate(R.id.searchFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menuFavorite -> {
                    navigationController.navigate(R.id.favoriteFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menuPersonal -> {
                    navigationController.navigate(R.id.accountFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }


    }
}
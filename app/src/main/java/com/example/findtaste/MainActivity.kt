package com.example.findtaste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.findtaste.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navigationController : NavController

    //firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

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

        //init firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, logout user

        /*
        btn.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }
         */
    }

    private fun checkUser() {
        //get CURRENT USER
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            //user is not logged in
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }else{
            //user is logged in
            val email = firebaseUser.email
            //binding.email.text = email
        }
    }
}
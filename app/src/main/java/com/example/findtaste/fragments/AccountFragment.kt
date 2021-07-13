package com.example.findtaste.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.findtaste.LoginActivity
import com.example.findtaste.R
import com.example.findtaste.databinding.FragmentAccountBinding
import com.example.findtaste.models.AccountViewModel
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private val accountViewModel: AccountViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(layoutInflater)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.profileBtnLogOut.setOnClickListener{
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro que desea salir?")
                .setNegativeButton("Cancelar") { view, _ ->
                    view.dismiss()
                }
                .setPositiveButton("Aceptar") { view, _ ->
                    firebaseAuth.signOut()
                    checkUser()
                    activity?.finish()

                    view.dismiss()
                }
                .setCancelable(false)
                .create()

            dialog.show()
        }
        return binding.root
    }


    private fun checkUser() {
        //get CURRENT USER
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }else{
            accountViewModel.mailLog.observe(viewLifecycleOwner, Observer { mail ->
                println("EMAIL: "+mail)
                binding.profileTextMail.setText(mail)
            })
        }
    }
}
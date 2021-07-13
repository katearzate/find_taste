package com.example.findtaste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.findtaste.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleSigninClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    //constants
    private companion object{
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //********* Google SignIn ***********
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSigninClient = GoogleSignIn.getClient(this, googleSignInOptions)

        //init Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //Google Signin Button
        binding.loginBtnEnter.setOnClickListener {
            Log.d(TAG, "onCreate: begin Google SIGNIN")
            val intent = googleSigninClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    private fun checkUser() {
        //check if user is logged in
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null){
            //user is already loggedin
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Result return from lauching the INTENT from GoogleSigninApi.getSiginIntent(...)
        if (requestCode == RC_SIGN_IN){
            Log.d(TAG,"onActivityResult: Google Signin Intent RESULT")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }
            catch (e: Exception){
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWGoogleAccount: begin FIREBASE AUTH w/ Google Account!")

        val credencial = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credencial)
            .addOnSuccessListener { authResult ->
                //Login SUCESS
                Log.d(TAG, "firebaseAuthWGoogleAccount: LOGGEDIN")

                val firebaseUser = firebaseAuth.currentUser
                //Get USER INFORMATION
                val uid = firebaseAuth!!.uid
                val email = firebaseUser!!.email

                Log.d(TAG, "firebaseAuthWGoogleAccount: UID: $uid")
                Log.d(TAG, "firebaseAuthWGoogleAccount: EMAIL: $email")

                //Check if the user is NEW or EXISTING
                if (authResult.additionalUserInfo!!.isNewUser){
                    Log.d(TAG, "firebaseAuthWGoogleAccount: Account CREATED.. \n$email")
                    Toast.makeText(this, "Cuenta creada...", Toast.LENGTH_LONG).show()
                }else{
                    Log.d(TAG, "firebaseAuthWGoogleAccount: Account EXISTED.. \n$email")
                    Toast.makeText(this, "Entrando a cuenta...", Toast.LENGTH_LONG).show()
                }
                //Begin profile activity...
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener{ e ->
                Log.d(TAG, "firebaseAuthWGoogleAccount: Loggin FAILED due to ${e.message}")
                Toast.makeText(this, "Fallo al entrar...", Toast.LENGTH_LONG).show()
            }
    }
}
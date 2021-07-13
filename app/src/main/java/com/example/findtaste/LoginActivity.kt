package com.example.findtaste

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.findtaste.databinding.ActivityLoginBinding
import com.example.findtaste.models.Tools.Companion.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleSigninClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private var lat = 0.0
    private var lng = 0.0

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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        //******************** Google SignIn ***************************
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
            myLocation()
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
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("lat", lat)
            intent.putExtra("lng", lng)
            startActivity(intent)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                "Permiso otorgado".toast(this)
            } else {
                "No se puede continuar sin la  ubicacion".toast(this)
                finish()
            }
        }
    }

    private fun myLocation() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
        }
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {
            if (hasGps) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F, object:
                    LocationListener {
                    override fun onLocationChanged(p0: Location) {
                        p0.let {
                            lat = it.latitude
                            lng = it.longitude
                        }
                    }

                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

                    override fun onProviderEnabled(provider: String) {}

                    override fun onProviderDisabled(provider: String) {}
                })
                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                localGpsLocation?.let {
                    lat = it.latitude
                    lng = it.longitude
                }
            }
            if (hasNetwork) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0F, object:
                    LocationListener {
                    override fun onLocationChanged(p0: Location) {
                        p0.let {
                            lat = it.latitude
                            lng = it.longitude
                        }
                    }

                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

                    override fun onProviderEnabled(provider: String) {}

                    override fun onProviderDisabled(provider: String) {}
                })
                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                localNetworkLocation?.let {
                    lat = it.latitude
                    lng = it.longitude
                }
            }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

}
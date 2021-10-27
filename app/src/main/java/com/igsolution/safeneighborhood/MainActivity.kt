package com.igsolution.safeneighborhood

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.igsolution.safeneighborhood.databinding.ActivityMainBinding
import com.igsolution.safeneighborhood.fragments.Home
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var Content: FrameLayout? = null
    private var mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.nav_inicio ->{
                val fragment = Home.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_mapa ->{
                val intent = Intent(this,MapsActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
                finish()
            }
            R.id.nav_biblioteca ->{
                val intent = Intent(this,perfil::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
                finish()
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Content = binding.content
        binding.bottomMenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = Home.newInstance()
        addFragment(fragment)



    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.Logoff -> {
                FirebaseAuth.getInstance().signOut()
                logoff()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun logoff(){
        val intent = Intent(this, formLogin::class.java)
        startActivity(intent)
        finish()
    }

    private fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }

}
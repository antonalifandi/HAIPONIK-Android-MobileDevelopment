package com.example.myapp


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.fragment.AboutFragment
import com.example.myapp.fragment.HomeFragment
import com.example.myapp.fragment.ProfileFragment
import com.example.myapp.fragment.WhatsappFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {


    val fragmentHome : Fragment = HomeFragment()
    val fragmentWhatsapp : Fragment = WhatsappFragment()
    val fragmentProfile : Fragment = ProfileFragment()
    val fragmentAbout : Fragment = AboutFragment()
    val fm : FragmentManager = supportFragmentManager
    var active : Fragment = fragmentHome

    lateinit var binding : ActivityMainBinding



    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var menu : Menu
    private lateinit var menuItem: MenuItem


  //  @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
      window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
      supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


      buttonNavigation()
    }

    private fun buttonNavigation() {
        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmentWhatsapp).hide(fragmentWhatsapp).commit()
        fm.beginTransaction().add(R.id.container, fragmentProfile).hide(fragmentProfile).commit()
        fm.beginTransaction().add(R.id.container, fragmentAbout).hide(fragmentAbout).commit()

        bottomNavigationView = binding.bottomview
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    callFrag(0, fragmentHome)
                }
                R.id.whatsapp -> {
                    callFrag(1, fragmentWhatsapp)
                }
                R.id.profile -> {
                    callFrag(2, fragmentProfile)
                }
                R.id.about -> {
                    callFrag(3, fragmentAbout)
                }
            }
            false
        }
    }
    private fun callFrag(index : Int, fragment: Fragment) {
        menuItem = menu.getItem(index)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }
}


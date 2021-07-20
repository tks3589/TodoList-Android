package com.aaron.todolist_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)  as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.mainFragment ->{
                    toolbar.menu.setGroupVisible(0,true)
                }
                else -> {
                    toolbar.menu.setGroupVisible(0,false)
                }
            }
        }
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.inflateMenu(R.menu.settings)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                 R.id.settings_item -> {
                    findNavController(navHostFragment).navigate(R.id.settingsFragment)
                 }
                R.id.settings_record -> {
                    findNavController(navHostFragment).navigate(R.id.historyFragment)
                }
            }
            true
        }
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

}
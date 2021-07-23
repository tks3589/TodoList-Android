package com.aaron.todolist_android

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)  as NavHostFragment
        navController = navHostFragment.navController
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
                R.id.settings_recycle -> {
                    findNavController(navHostFragment).navigate(R.id.recycleFragment)
                }
            }
            true
        }
        toolbar.setupWithNavController(navController, appBarConfiguration)

        processToolBarStatus()
    }

    private fun setTheme(){
         val uiDataStore = UIModePreference(this)
         lifecycleScope.launch {
             if(uiDataStore.uiMode.first())
                 AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
             else
                 AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
         }
    }


    private fun processToolBarStatus(){
        when(navController.currentDestination?.id){
            R.id.mainFragment ->{
                toolbar.menu.setGroupVisible(0,true)
            }
            else -> {
                toolbar.menu.setGroupVisible(0,false)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreate()
    }
}
package nit.school.lifeloom

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import nit.school.lifeloom.databinding.ActivityMainBinding
import nit.school.lifeloom.logic.showToast


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this,
            R.layout.activity_main)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            // Set toolbar title/app title
            title = "Toolbar Title"

            // Set action bar/toolbar sub title
            subtitle = "Toolbar sub title"

            // Display the app icon in action bar/toolbar
            setDisplayShowHomeEnabled(true)
            setIcon(R.mipmap.ic_launcher_round)
            setDisplayUseLogoEnabled(true)
        }

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_incremental, R.id.navigation_quantity, R.id.navigation_time))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return (Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
                || super.onSupportNavigateUp())
    }

    fun addNewActivityButton(view: View) {
        navController.navigate(R.id.addingActivityFragment)


        /* Moze i ovo, ali iznad metoda je puno bolja jer ne drzi u stacku kad se promijeni
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, AddingActivityFragment())
           .commitAllowingStateLoss()
         */
    }

    fun deleteActivity(view: View) {
        showToast(applicationContext, "OVDJE SE BRISE, NEKAKO U FRAGMENTU IMPLEMENTIRATI")
    }
}

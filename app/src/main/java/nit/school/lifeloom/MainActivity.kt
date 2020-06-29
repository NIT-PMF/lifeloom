package nit.school.lifeloom

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import nit.school.lifeloom.databinding.ActivityMainBinding
import androidx.databinding.DataBindingUtil.setContentView
import kotlinx.android.synthetic.main.activity_main.*
import nit.school.lifeloom.logic.showToast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
            setLogo(R.mipmap.ic_launcher)
            setDisplayUseLogoEnabled(true)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun addNewActivityButton(view: View) {
        showToast(applicationContext, "Ovdje dodati novu aktivnost koju user napravi")
    }
}

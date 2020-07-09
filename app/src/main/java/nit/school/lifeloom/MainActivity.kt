package nit.school.lifeloom

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.*
import nit.school.lifeloom.database.AppDB
import nit.school.lifeloom.database.dao.IncrementCategoryDao
import nit.school.lifeloom.database.dao.QuantityCategoryDao
import nit.school.lifeloom.database.dao.TimeCategoryDao
import nit.school.lifeloom.databinding.ActivityMainBinding
import nit.school.lifeloom.logic.showToast
import nit.school.lifeloom.singleton.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var timeDb: TimeCategoryDao
    private lateinit var quantityDb: QuantityCategoryDao
    private lateinit var incrementDb: IncrementCategoryDao
    private lateinit var job: Job
    private lateinit var uiScope: CoroutineScope

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timeDb = AppDB.getInstance(applicationContext).timeCategoryDao
        quantityDb = AppDB.getInstance(applicationContext).quantityCategoryDao
        incrementDb = AppDB.getInstance(applicationContext).incrementCategoryDao

        job = Job()
        uiScope = CoroutineScope(Dispatchers.Main + job)

        getFromDatebase()
        Log.i("messege", timePeriodSingleton.toString())



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

    private fun getFromDatebase() {
        runBlocking{withContext(Dispatchers.IO){
            val timeList = timeDb.getAll()
            val quantityList = quantityDb.getAll()
            val incrementList = incrementDb.getAll()

            for (quantity in quantityList){
                val date = Calendar.getInstance()
                date.timeInMillis = quantity.date
                quantitySingleton.addActivity(QuantityCategory(0,quantity.name, quantity.description, date, mutableListOf(),quantity.value, quantity.unit))
            }
            for (increment in incrementList){
                val date = Calendar.getInstance()
                date.timeInMillis = increment.date
                incrementSingleton.addActivity(IncrementCategory(0, increment.name, increment.description, date, mutableListOf(), increment.value, increment.increment))
            }
            for (time in timeList){
                val date = Calendar.getInstance()
                val startTime = Calendar.getInstance()
                val endTime = Calendar.getInstance()
                date.timeInMillis = time.date
                startTime.timeInMillis = time.startTime
                endTime.timeInMillis = time.endTime
                timePeriodSingleton.addActivity(TimeCategory(0,time.name,time.description, date, mutableListOf(),  startTime, endTime, ((time.endTime - time.startTime) / 1000)))
                Log.i("message",time.toString());
            }
            timeList

 }}
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

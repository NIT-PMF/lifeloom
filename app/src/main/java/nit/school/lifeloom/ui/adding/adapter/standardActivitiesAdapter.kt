package nit.school.lifeloom.ui.adding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.standard_activity_list_item.view.*
import kotlinx.coroutines.*
import nit.school.lifeloom.MainActivity
import nit.school.lifeloom.R
import nit.school.lifeloom.database.AppDB
import nit.school.lifeloom.database.dao.IncrementCategoryDao
import nit.school.lifeloom.database.dao.QuantityCategoryDao
import nit.school.lifeloom.database.dao.TimeCategoryDao
import nit.school.lifeloom.database.entity.IncrementTable
import nit.school.lifeloom.database.entity.QuantityTable
import nit.school.lifeloom.database.entity.TimeTable
import nit.school.lifeloom.logic.showToast
import nit.school.lifeloom.singleton.*
import nit.school.lifeloom.ui.adding.adapter.StandardActivitiesAdapter.*
import java.util.*


class StandardActivitiesAdapter(private val activitesList: List<Activity>, private val context: Context, private val navController: NavController) :  RecyclerView.Adapter<StandardActivityViewHolder>() {
    private lateinit var timeDb: TimeCategoryDao
    private lateinit var quantityDb: QuantityCategoryDao
    private lateinit var incrementDb: IncrementCategoryDao
    private lateinit var job: Job
    private lateinit var uiScope: CoroutineScope

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandardActivityViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.standard_activity_list_item, parent, false)
        return StandardActivityViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return activitesList.size
    }


    @InternalCoroutinesApi
    override fun onBindViewHolder(holder: StandardActivityViewHolder, position: Int) {

        timeDb = AppDB.getInstance((context as MainActivity).applicationContext).timeCategoryDao
        quantityDb = AppDB.getInstance((context as MainActivity).applicationContext).quantityCategoryDao
        incrementDb = AppDB.getInstance((context as MainActivity).applicationContext).incrementCategoryDao

        job = Job()
        uiScope = CoroutineScope(Dispatchers.Main + job)

        holder.name.text = activitesList[position].name
        holder.activityInfo.text = activitesList[position].description
        holder.add_btn.setOnClickListener { view ->
            //val bundle = bundleOf("activityName" to name.text.toString())
            //navController.navigate(R.id.activityTrackerFragment, bundle)
            if(activitesList[position].name == "Trcanje"){
                quantitySingleton.addActivity(QuantityCategory(0, activitesList[position].name, "Kilometri koje sam trcao",  Calendar.getInstance(),
                        mutableListOf(),0,"km"))
                runBlocking { withContext(Dispatchers.IO){
                    quantityDb.insert(QuantityTable( name = activitesList[position].name, description = "Kilometri koje sam trcao",  date = Calendar.getInstance().timeInMillis,
                            properties = "",value = 0, unit= "km"))
                } }
            }else if(activitesList[position].name == "Skakanje"){
                incrementSingleton.addActivity(IncrementCategory(0, activitesList[position].name, "Centimetri koje sam skocio",  Calendar.getInstance(),
                        mutableListOf(),0,1))
                runBlocking { withContext(Dispatchers.IO){
                    incrementDb.insert(IncrementTable( name = activitesList[position].name, description = "Centimetri koje sam skocio",  date = Calendar.getInstance().timeInMillis,
                            properties = "",value = 0, increment= 1))
                } }
            }else if(activitesList[position].name == "Spavanje"){
                timePeriodSingleton.addActivity(TimeCategory(0, activitesList[position].name, "Sati koje sam spavao", Calendar.getInstance(), mutableListOf(), Calendar.getInstance(), Calendar.getInstance(), 0))
                runBlocking { withContext(Dispatchers.IO){
                    timeDb.insert(TimeTable( name = activitesList[position].name, description = "Sati koje sam spavao",  date = Calendar.getInstance().timeInMillis,
                            properties = "", startTime = Calendar.getInstance().timeInMillis, endTime = Calendar.getInstance().timeInMillis,value = 0))
                } }
            }else if(activitesList[position].name == "Tecnost"){
                quantitySingleton.addActivity(QuantityCategory(0, activitesList[position].name, "Tecnosti danas ispio",  Calendar.getInstance(),
                        mutableListOf(),0,"L"))
                runBlocking { withContext(Dispatchers.IO){
                    quantityDb.insert(QuantityTable( name = activitesList[position].name, description = "Tecnosti danas ispio",  date = Calendar.getInstance().timeInMillis,
                            properties = "",value = 0, unit= "L"))
                } }
            }

            showToast(context, "Activity Added")
        }
    }

    //Postavljanje sa layouta
    class StandardActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.standard_name_tv
        val activityInfo = itemView.activity_tv
        val add_btn = itemView.add_standard_btn
    }


    }
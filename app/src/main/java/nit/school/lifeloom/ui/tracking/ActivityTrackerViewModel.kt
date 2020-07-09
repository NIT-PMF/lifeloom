package nit.school.lifeloom.ui.tracking

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import nit.school.lifeloom.MainActivity
import nit.school.lifeloom.database.AppDB
import nit.school.lifeloom.database.dao.IncrementCategoryDao
import nit.school.lifeloom.database.dao.QuantityCategoryDao
import nit.school.lifeloom.database.dao.TimeCategoryDao
import nit.school.lifeloom.database.entity.IncrementTable
import nit.school.lifeloom.database.entity.QuantityTable
import nit.school.lifeloom.database.entity.TimeTable
import nit.school.lifeloom.singleton.*
import java.util.*


@InternalCoroutinesApi
class ActivityTrackerViewModel(name: String, state: String, applicationContext: Context) : ViewModel(){


    var value: Int
    var name = name
    var running = false
    var baseTime:Long = 0
    var propertyList:MutableList<Property> = mutableListOf()
    private lateinit var timeDb: TimeCategoryDao
    private lateinit var quantityDb: QuantityCategoryDao
    private lateinit var incrementDb: IncrementCategoryDao
    private lateinit var job: Job
    private lateinit var uiScope: CoroutineScope


    init{
        if(state == "increment") {
            val position = incrementSingleton.updatePosition(Calendar.getInstance(), name)
            if (position == -1) {
                value = 0
            } else {
                value = incrementSingleton.getActivityByPosition(position)?.value ?: 0
            }
        }else if(state == "quantity"){
            val position = quantitySingleton.updatePosition(Calendar.getInstance(), name)
            Log.i("messege position", position.toString())
            propertyList = quantitySingleton.propertyList(name)
            if (position == -1) {
                value = 0
            } else {
                value = quantitySingleton.getActivityByPosition(position)!!.value
            }
        }else{
            value = 0
        }

        timeDb = AppDB.getInstance(applicationContext).timeCategoryDao
        quantityDb = AppDB.getInstance(applicationContext).quantityCategoryDao
        incrementDb = AppDB.getInstance(applicationContext).incrementCategoryDao

        job = Job()
        uiScope = CoroutineScope(Dispatchers.Main + job)


    }

    fun addIncrement( id:Number, name:String, description:String, increment:Int) {
        val position = incrementSingleton.updatePosition(Calendar.getInstance(), name)
        if(position == -1) {
            incrementSingleton.addActivity(IncrementCategory(id, name, description, Calendar.getInstance(), listOf(), value, increment))
            runBlocking { withContext(Dispatchers.IO){
                incrementDb.insert(IncrementTable(id as Int, name, description, Calendar.getInstance().timeInMillis, "", value, increment))
            }
            }
        }else{
            incrementSingleton.updatePositionValue(position, value)
            runBlocking { withContext(Dispatchers.IO){
                incrementDb.update(value, incrementSingleton.getActivityByPosition(position)!!.name, incrementSingleton.getActivityByPosition(position)!!.date.time.getTime())
            } }
        }
    }
    fun addQuantity( id:Number, name:String, description:String, unit:String) {
        val position = quantitySingleton.updatePosition(Calendar.getInstance(), name)
        if(position == -1) {
            quantitySingleton.addActivity(QuantityCategory(id, name, description, Calendar.getInstance(), mutableListOf(), value, unit))
            runBlocking { withContext(Dispatchers.IO){
                quantityDb.insert(QuantityTable(id.toInt(), name, description, Calendar.getInstance().timeInMillis, "", value, unit))
            } }
        }else{
            quantitySingleton.updatePositionValue(position, value)
            runBlocking { withContext(Dispatchers.IO){
                quantityDb.update(value, name, quantitySingleton.getActivityByPosition(position)!!.date.time.getTime())
            } }
        }
    }

    fun addTimeStart( id:Number, name:String, description:String, time:Long){
        baseTime = time
        val date = Calendar.getInstance()
        timePeriodSingleton.addActivity(TimeCategory(id, name, description, Calendar.getInstance(), listOf(), date, date, 0))
        runBlocking { withContext(Dispatchers.IO){
            timeDb.insert(TimeTable(name = name, description = description, date = Calendar.getInstance().timeInMillis, properties = "", startTime = date.timeInMillis, endTime = date.timeInMillis, value = 0))

        }
        }
    }
    fun addTimeEnd(){
        val position = timePeriodSingleton.updatePosition(name)
        if(position != -1) {
            val date =  Calendar.getInstance()
            timePeriodSingleton.updatePositionValue(position, date)
            runBlocking { withContext(Dispatchers.IO) {
                timeDb.update((date.timeInMillis - timePeriodSingleton.getActivityByPosition(position)!!.startTime.timeInMillis)/1000,
                        timePeriodSingleton.getActivityByPosition(position)!!.startTime.timeInMillis,
                        date.timeInMillis, name, timePeriodSingleton.getActivityByPosition(position)!!.date.timeInMillis)
            } }
        }
    }

    fun addPropertyToQuantitySingelton(nameOfProperty:String, from:String, to:String){
        quantitySingleton.updateProperty(name, nameOfProperty, from, to)
        Log.i("message", quantitySingleton.getActivities().toString())
    }



}
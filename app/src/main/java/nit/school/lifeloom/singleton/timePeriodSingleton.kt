package nit.school.lifeloom.singleton

import android.os.SystemClock
import android.util.Log
import java.util.*

/** Sadrzi podatke o aktivnostima **/
object timePeriodSingleton {
    private var activityList: MutableList<TimeCategory?> = mutableListOf()

    //Konstruktor za aktivnosti
    init {
    }

    //Vracanje liste s podacima
    fun getActivities(): MutableList<TimeCategory?> {
        return activityList
    }

    //Vracanje aktivnosti po ID-u
    fun getActivityById(TimeCategoryId: Number): TimeCategory? {
        return activityList.find { it?.id == TimeCategoryId }
    }

    //Ukupan Broj Aktivnosti
    fun getActivitySize(): Number {
        return activityList.size
    }

    //Dodaj Novu Aktivnosti
    fun addActivity(timeCategory: TimeCategory) {
        activityList.add(timeCategory)
    }
    //Vraca indes za update u suprotnom -1
    fun updatePosition(name:String): Int {
        for (i in 0..(activityList.size-1)){
            if(activityList[i]?.endTime == activityList[i]?.startTime
                    && activityList[i]!!.name == name){
                return i
            }
        }
        return -1
    }

    fun updatePositionValue(position:Int, date: Calendar, time:Long){
        activityList[position]?.endTime = date
        activityList[position]?.value = SystemClock.elapsedRealtime() - time
    }


    fun getActivityByPosition(position: Int): TimeCategory? {
        return activityList[position]
    }

    fun updateProperty(name:String, nameOfProperty: String,from:String, to:String ){
        val newProperty = Property(nameOfProperty, from, to)
        for (time in activityList){
            if (time?.name == name){
                time.properties.add(newProperty)
            }
        }
    }

    fun propertyList(name: String): MutableList<Property> {
        var newPropertyList:MutableList<Property> = mutableListOf()
        for(time in activityList){
            if(time?.name == name) {
                for(property in time.properties){
                    if (property != null) {
                        newPropertyList.add(property)
                    }
                }
                break
            }
        }
        return newPropertyList
    }




}

data class TimeCategory(val id: Number, val name: String, val description: String, val date: Calendar, val properties: MutableList<Property>, val startTime: Calendar, var endTime: Calendar, var value: Long)
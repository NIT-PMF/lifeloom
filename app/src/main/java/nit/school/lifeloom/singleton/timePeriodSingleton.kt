package nit.school.lifeloom.singleton

import androidx.lifecycle.LiveData
import java.util.*
import kotlin.math.abs

/** Sadrzi podatke o aktivnostima **/
object timePeriodSingleton {
    private var activityList: MutableList<TimeCategory?> = mutableListOf()

    //Konstruktor za aktivnosti
    init {
        activityList.add(
            TimeCategory(
                1,
                "Kalorije Dnevno",
                "Pratim Kalorije",
                Calendar.getInstance(),
                listOf(null),
                Calendar.getInstance(),
                Calendar.getInstance(),
                    10001

            ))
        activityList.add(
            TimeCategory(
                1,
                "Kalorije Dnevno",
                "Pratim Kalorije",
                Calendar.getInstance(),
                listOf(null),
                Calendar.getInstance(),
                Calendar.getInstance(),
                    120000
            ))
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
    fun addActivity(IncrementCategory: TimeCategory) {
        activityList.add(IncrementCategory)
    }
    //Vraca indes za update u suprotnom -1
    fun updatePosition(name:String): Int {
        for (i in 0..activityList.size){
            if(activityList[i]?.endTime == activityList[i]?.startTime
                    && activityList[i]!!.name == name){
                return i
            }
        }
        return -1
    }

    fun updatePositionValue(position:Int, date: Calendar){
        activityList[position]?.endTime = date
        activityList[position]?.value = (date.time.getTime() -activityList[position]!!.startTime.time.getTime()).toInt()
    }


    fun getActivityByPosition(position: Int): TimeCategory? {
        return activityList[position]
    }



}

data class TimeCategory(val id: Number, val name: String, val description: String, val date: Calendar, val properties: List<Property?>, val startTime: Calendar, var endTime: Calendar, var value:Int)
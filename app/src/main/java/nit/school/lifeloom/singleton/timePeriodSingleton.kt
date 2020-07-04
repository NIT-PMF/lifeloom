package nit.school.lifeloom.singleton

import androidx.lifecycle.LiveData
import java.util.*

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
                Calendar.getInstance()

            ))
        activityList.add(
            TimeCategory(
                1,
                "Kalorije Dnevno",
                "Pratim Kalorije",
                Calendar.getInstance(),
                listOf(null),
                Calendar.getInstance(),
                Calendar.getInstance()
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

}

data class TimeCategory(val id: Number, val name: String, val description: String, val date: Calendar, val properties: List<Property?>, val startTime: Calendar, val endTime: Calendar)
package nit.school.lifeloom.singleton

import androidx.lifecycle.LiveData
import java.util.*

/** Sadrzi podatke o aktivnostima **/
object incrementSingleton {
    private var activityList: MutableList<IncrementCategory?> = mutableListOf()

    //Konstruktor za aktivnosti
    init {
        activityList.add(
            IncrementCategory(
                1,
                "Zadace",
                "Uradio zadacu",
                Calendar.getInstance(),
                listOf(Property(
                    1,
                    "Odmor",
                    2
                )),
                1
            )
        )
        activityList.add(
        IncrementCategory(
                    1,
            "Popio tablete",
            "Pratio dnevno",
            Calendar.getInstance(),
            listOf(Property(
                1,
                "Odmor",
                10
            )),
            4
        ))
    }

    //Vracanje liste s podacima
    fun getActivities(): MutableList<IncrementCategory?> {
        return activityList
    }

    //Vracanje aktivnosti po ID-u
    fun getActivityById(IncrementCategoryId: Number): IncrementCategory? {
        return activityList.find { it?.id == IncrementCategoryId }
    }

    //Ukupan Broj Aktivnosti
    fun getActivitySize(): Number {
        return activityList.size
    }

    //Dodaj Novu Aktivnosti
    fun addActivity(IncrementCategory: IncrementCategory) {
        activityList.add(IncrementCategory)
    }

}

data class IncrementCategory(val id: Number, val name: String, val description: String, val date: Calendar, val properties: List<Property?>, val value: Int)
data class Property(val id: Number, val name: String, val value: Int)
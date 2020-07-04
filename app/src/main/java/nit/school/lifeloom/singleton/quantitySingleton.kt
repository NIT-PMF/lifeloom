package nit.school.lifeloom.singleton

import androidx.lifecycle.LiveData
import java.util.*

/** Sadrzi podatke o aktivnostima **/
object quantitySingleton {
    private var activityList: MutableList<QuantityCategory?> = mutableListOf()

    //Konstruktor za aktivnosti
    init {
        activityList.add(
            QuantityCategory(
                1,
                "Kalorije Dnevno",
                "Pratim Kalorije",
                Calendar.getInstance(),
                listOf(Property(
                    1,
                    "Odmor",
                    2
                )),
                12
            ))
        activityList.add(
        QuantityCategory(
            1,
            "Otkucaj Srca",
            "Pratio dnevno",
            Calendar.getInstance(),
            listOf(Property(
                1,
                "Odmor",
                10
            ),
            Property(
                2,
                "Vjezbe",
                15
            )),
            12
        ))
    }

    //Vracanje liste s podacima
    fun getActivities(): MutableList<QuantityCategory?> {
        return activityList
    }

    //Vracanje aktivnosti po ID-u
    fun getActivityById(QuantityCategoryId: Number): QuantityCategory? {
        return activityList.find { it?.id == QuantityCategoryId }
    }

    //Ukupan Broj Aktivnosti
    fun getActivitySize(): Number {
        return activityList.size
    }

    //Dodaj Novu Aktivnosti
    fun addActivity(IncrementCategory: QuantityCategory) {
        activityList.add(IncrementCategory)
    }

}

data class QuantityCategory(val id: Number, val name: String, val description: String, val date: Calendar, val properties: List<Property?>, val value: Int)
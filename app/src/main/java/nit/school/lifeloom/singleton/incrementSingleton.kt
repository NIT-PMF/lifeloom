package nit.school.lifeloom.singleton

import androidx.lifecycle.LiveData
import java.lang.Math.abs
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
                1,
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
            4,
                2
        ))

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
                        4,
                        2
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

    fun getActivityByPosition(position: Int): IncrementCategory? {
        return activityList[position]
    }

    //Ukupan Broj Aktivnosti
    fun getActivitySize(): Number {
        return activityList.size
    }

    //Dodaj Novu Aktivnosti
    fun addActivity(IncrementCategory: IncrementCategory) {
        activityList.add(IncrementCategory)
    }

    //Vraca indes za update u suprotnom -1
    fun updatePosition(date:Calendar, name:String): Int {
        for (i in 0..activityList.size){
            if(abs(activityList[i]?.date!!.time.getTime() - date.time.getTime()) < 86400
                    && activityList[i]!!.name == name){
                return i
            }
        }
        return -1
    }

    fun updatePositionValue(position:Int, value:Int){
        activityList[position]?.value = value
    }


}

data class IncrementCategory(val id: Number, val name: String, val description: String, val date: Calendar, val properties: List<Property?>, var value: Int, val increment: Int)
data class Property(val id: Number, val name: String, val value: Int)
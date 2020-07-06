package nit.school.lifeloom.singleton

import androidx.lifecycle.LiveData
import java.util.*
import kotlin.math.abs

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
                12,
            "bam"
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
            12,
                "bam"
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
                        12,
                        "bam"
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
                        12,
                        "bam"
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


    fun getActivityByPosition(position: Int): QuantityCategory? {
        return activityList[position]
    }


}

data class QuantityCategory(val id: Number, val name: String, val description: String, val date: Calendar, val properties: List<Property?>, var value: Int, val unit:String)
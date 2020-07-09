package nit.school.lifeloom.singleton

import java.text.SimpleDateFormat
import java.util.*

/** Sadrzi podatke o aktivnostima **/
object incrementSingleton {
    private var activityList: MutableList<IncrementCategory?> = mutableListOf()

    //Konstruktor za aktivnosti
    init {
    }

    //Vracanje liste s podacima
    fun getActivities(): MutableList<IncrementCategory?> {
        return activityList.sortedByDescending{ it?.date}.toMutableList()
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
    fun updatePosition(date: Calendar, name: String): Int {
        val formatter: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")

        for (i in 0..(activityList.size - 1)) {
            if ((formatter.format(date.time) == formatter.format((activityList[i]!!.date.time))) &&
                activityList[i]!!.name == name
            ) {
                return i
            }
        }
                return -1
        }

            fun updatePositionValue(position: Int, value: Int) {
                activityList[position]?.value = value
            }

            fun updateProperty(name: String, nameOfProperty: String, from: String, to: String) {
                val newProperty = Property(nameOfProperty, from, to)
                for (increment in activityList) {
                    if (increment?.name == name) {
                        increment.properties.add(newProperty)
                    }
                }
            }

            fun propertyList(name: String): MutableList<Property> {
                var newPropertyList: MutableList<Property> = mutableListOf()
                for (increment in activityList) {
                    if (increment?.name == name) {
                        for (property in increment.properties) {

                            if (property != null) {
                                newPropertyList.add(property)
                            }
                        }
                        break
                    }
                }
                return newPropertyList }

    fun  deleteFromActivity(name:String){

        activityList = activityList.filter { it?.name !== name}.toMutableList()
    }
}

data class IncrementCategory(val id: Number, val name: String, val description: String, val date: Calendar, val properties: MutableList<Property>, var value: Int, val increment: Int)
data class Property(val name: String, val from:String, val to:String)
package nit.school.lifeloom.singleton

import java.util.*
import kotlin.math.abs

/** Sadrzi podatke o aktivnostima **/
object quantitySingleton {
    private var activityList: MutableList<QuantityCategory?> = mutableListOf()

    //Konstruktor za aktivnosti
    init {
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
    fun addActivity(quantityCategory: QuantityCategory) {
        activityList.add(quantityCategory)
    }

    //Vraca indes za update u suprotnom -1
    fun updatePosition(date:Calendar, name:String): Int {
        for (i in 0..(activityList.size-1)){
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

    fun updateProperty(name:String, nameOfProperty: String,from:String, to:String ){
        val newProperty = Property(nameOfProperty, from, to)
        for (quantity in activityList){
            if (quantity?.name == name){
                quantity.properties.add(newProperty)
            }
        }
    }

    fun propertyList(name: String): MutableList<Property> {
        var newPropertyList:MutableList<Property> = mutableListOf()
        for(quantity in activityList){
            if(quantity?.name == name) {
                for(property in quantity.properties){
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

data class QuantityCategory(val id: Number, val name: String, val description: String, val date: Calendar, val properties: MutableList<Property?>, var value: Int, val unit: String)
package nit.school.lifeloom.ui.tracking

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import nit.school.lifeloom.singleton.*
import java.util.*


class ActivityTrackerViewModel(name: String, state: String) : ViewModel(){


    var value: Int
    var name = name
    var running = false
    var baseTime:Long = 0

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
            if (position == -1) {
                value = 0
            } else {
                value = quantitySingleton.getActivityByPosition(position)?.value ?: 0
            }
        }else{
            value = 0
        }
    }

    fun addIncrement( id:Number, name:String, description:String, increment:Int) {
        val position = incrementSingleton.updatePosition(Calendar.getInstance(), name)
        if(position == -1) {
            incrementSingleton.addActivity(IncrementCategory(id, name, description, Calendar.getInstance(), listOf(), value, increment))
        }else{
            incrementSingleton.updatePositionValue(position, value)
        }
    }
    fun addQuantity( id:Number, name:String, description:String, unit:String) {
        val position = quantitySingleton.updatePosition(Calendar.getInstance(), name)
        if(position == -1) {
            quantitySingleton.addActivity(QuantityCategory(id, name, description, Calendar.getInstance(), listOf(), value, unit))
        }else{
            quantitySingleton.updatePositionValue(position, value)
        }
    }

    fun addTimeStart( id:Number, name:String, description:String, time:Long){
        baseTime = time
        val date = Calendar.getInstance()
        timePeriodSingleton.addActivity(TimeCategory(id, name, description, Calendar.getInstance(), listOf(), date, date, 0))
    }
    fun addTimeEnd(){
        val position = timePeriodSingleton.updatePosition(name)
        timePeriodSingleton.updatePositionValue(position, Calendar.getInstance())
    }
}
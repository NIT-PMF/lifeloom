package nit.school.lifeloom.ui.tracking


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ActivityTrackerViewModelFactory(private val name: String, private  val state:String): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActivityTrackerViewModel::class.java)) {
            return ActivityTrackerViewModel(name, state) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}
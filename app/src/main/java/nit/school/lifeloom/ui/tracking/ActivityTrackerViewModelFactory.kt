package nit.school.lifeloom.ui.tracking


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.InternalCoroutinesApi

class ActivityTrackerViewModelFactory(private val name: String, private  val state:String, private val applicationContext: Context): ViewModelProvider.NewInstanceFactory() {
    @InternalCoroutinesApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActivityTrackerViewModel::class.java)) {
            return ActivityTrackerViewModel(name, state, applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}
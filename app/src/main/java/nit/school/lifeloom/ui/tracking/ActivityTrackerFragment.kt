package nit.school.lifeloom.ui.tracking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import nit.school.lifeloom.MainActivity
import nit.school.lifeloom.R
import nit.school.lifeloom.databinding.FragmentActivityTrackerBinding
import nit.school.lifeloom.logic.showToast
import java.lang.Double.parseDouble
import java.util.*
import kotlin.text.toInt as toInt

class ActivityTrackerFragment : Fragment() {

    private lateinit var binding: FragmentActivityTrackerBinding
    private lateinit var viewModel: ActivityTrackerViewModel
    private lateinit var viewModelFactory: ActivityTrackerViewModelFactory
    var state:String = ""
    var increment:String = ""
    var unit:String = ""
    var valueList:MutableList<Int> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_tracker, container, false)

        val activityName: String = arguments?.getString("activityName") ?: "Not Set"
        state = arguments?.getString("state") ?: "Not set"

        viewModelFactory = ActivityTrackerViewModelFactory(activityName, state)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(ActivityTrackerViewModel::class.java)




        //Ovdje vracam naziv aktivnosti, ako zelis jos vratiti provjeri adapter i morat ces dodatni argument u navigation dodati
        val dateInSeconds = arguments?.getString("dateInSeconds") ?: "Not set"
        val description = arguments?.getString("description") ?: "Not set"
        val id = arguments?.getString("id") ?: "Not set"
        val values = arguments?.getString("value") ?: "Not set"


        val list = (dateInSeconds.split(","))
        val listValues = (values.split(","))
        val dateList:MutableList<Date> = mutableListOf()

        for (date in list) {
            if (date != "") {
                dateList.add(Date(date.toLong()));
            }
        }
        for (value in listValues){
            if(value != ""){
                valueList.add(value.toInt())
            }
        }

        Log.i("messeage", valueList.toString())

        binding.activityNameTv.text = activityName
        (activity as MainActivity).supportActionBar?.title = activityName

        if(state == "increment") {
            binding.incrementLayout.visibility = View.VISIBLE
            binding.quantityLayout.visibility = View.GONE
            binding.timeLayout.visibility = View.GONE

            increment = arguments?.getString("increment") ?: "0"

            binding.incrementDescription.text = description
            binding.incrementValue.text = viewModel.value.toString()

            binding.substractButton.setOnClickListener{substractAddClick(true, id, activityName, description)}
            binding.addButton.setOnClickListener{substractAddClick(false, id, activityName, description)}

        }else if(state == "quantity"){
            binding.quantityLayout.visibility = View.VISIBLE
            binding.incrementLayout.visibility = View.GONE
            binding.timeLayout.visibility = View.GONE

            binding.quantityDescription.text = description
            binding.quantityValue.text = viewModel.value.toString()
            binding.quantityUnit.text = unit

            binding.quantityButton.setOnClickListener{quantityAdd(id, activityName, description)}

            unit = arguments?.getString("unit") ?: "0"
        }else{
            binding.quantityLayout.visibility = View.GONE
            binding.incrementLayout.visibility = View.GONE
            binding.timeLayout.visibility = View.VISIBLE
            binding.timeEnd.visibility = View.GONE

            binding.timeDescription.text = description

            binding.timeBegin.setOnClickListener{startTimer(id, activityName, description)}
            binding.timeEnd.setOnClickListener{endTimer()}
        }


        return binding.root
    }

    private fun endTimer() {
        viewModel.addTimeEnd()
        binding.timeStart.text = "Odbrojavanje nije pocelo"
        binding.timeBegin.visibility = View.VISIBLE
        binding.timeEnd.visibility = View.GONE


    }

    private fun startTimer(id: String, activityName: String, description: String) {
        viewModel.addTimeStart(id.toInt(), activityName, description)
        binding.timeStart.text = "Odbrojavanje je pocelo"
        binding.timeBegin.visibility = View.GONE
        binding.timeEnd.visibility = View.VISIBLE
    }


    private fun quantityAdd(id: String, activityName: String, description: String) {
        val prvValue = binding.quantityValue.text.toString()
        val newValue  = binding.quantityInput.text.toString()
        var value:Int = 0
        var numeric = true

        try {
            val num = parseDouble(newValue)
        } catch (e: NumberFormatException) {
            numeric = false
        }

        if(numeric) {
            value = newValue.toInt() + prvValue.toInt()
            onSubmitValue(id, activityName, description)
            viewModel.value = value
        }else{
            showToast(requireContext(), "Nije broj")
        }
        binding.quantityValue.text = viewModel.value.toString()
    }

    private fun onSubmitValue(id:String, name:String, description:String) {
        if(state == "increment"){
            viewModel.addIncrement(id.toInt(), name, description, increment.toInt())
        }else if(state == "quantity"){
            viewModel.addQuantity(id.toInt(), name, description, unit)
        }
    }


    private fun substractAddClick(sustract:Boolean, id:String, name:String, description:String) {
        val prvValue = binding.incrementValue.text.toString()
        val value:Int
        if (sustract) {
             value = prvValue.toInt() - increment.toInt()
        }
        else{
            value =  prvValue.toInt() + increment.toInt()
        }
        viewModel.value = value
        binding.incrementValue.text = viewModel.value.toString()
        onSubmitValue(id, name, description)
    }

}

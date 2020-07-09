package nit.school.lifeloom.ui.adding

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil.inflate
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import nit.school.lifeloom.MainActivity
import nit.school.lifeloom.R
import nit.school.lifeloom.database.AppDB
import nit.school.lifeloom.database.dao.IncrementCategoryDao
import nit.school.lifeloom.database.dao.QuantityCategoryDao
import nit.school.lifeloom.database.dao.TimeCategoryDao
import nit.school.lifeloom.database.entity.IncrementTable
import nit.school.lifeloom.database.entity.QuantityTable
import nit.school.lifeloom.database.entity.TimeTable
import nit.school.lifeloom.databinding.FragmentAddingActivityBinding
import nit.school.lifeloom.logic.showToast
import nit.school.lifeloom.singleton.*
import nit.school.lifeloom.ui.adding.adapter.StandardActivitiesAdapter
import java.util.*

class AddingActivityFragment : Fragment() {

    private lateinit var binding: FragmentAddingActivityBinding
    private lateinit var spinner: Spinner
    private lateinit var timeDb: TimeCategoryDao
    private lateinit var quantityDb: QuantityCategoryDao
    private lateinit var incrementDb: IncrementCategoryDao
    private lateinit var job: Job
    private lateinit var uiScope: CoroutineScope


    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, R.layout.fragment_adding_activity, container, false)

        timeDb = AppDB.getInstance((requireActivity() as MainActivity).applicationContext).timeCategoryDao
        quantityDb = AppDB.getInstance((requireActivity() as MainActivity).applicationContext).quantityCategoryDao
        incrementDb = AppDB.getInstance((requireActivity() as MainActivity).applicationContext).incrementCategoryDao

        job = Job()
        uiScope = CoroutineScope(Dispatchers.Main + job)

        //Brisanje + dugma
        val buttonAdd : ImageButton = (requireActivity() as MainActivity).findViewById(R.id.add_activity_btn)
        buttonAdd.visibility = View.INVISIBLE

        //Provjera switcha i mijenjanje zavisno od izabranog
        val category_switch: Switch = binding.customDefaultSwitch

        category_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.defaultCategoryLayout.visibility = View.VISIBLE
                binding.customCategoryLayout.visibility = View.GONE
            }
            else {
                binding.defaultCategoryLayout.visibility = View.GONE
                binding.customCategoryLayout.visibility = View.VISIBLE
            }
        }

        //Postavljanje default liste
        val activities = activitiesSingleton //Promijeniti kad budemo stavljali
        val recyclerView = binding.standardCategoryRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = StandardActivitiesAdapter((activities.getActivities().toList() as List<Activity>), requireContext(), requireActivity().findNavController(R.id.nav_host_fragment))



        //Dodavanje stvari u spinner
        spinner = binding.noActivitySpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.activity_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        //Kad klikne na checkbox pokazati dodatne konfiguracije
        binding.addInfoCb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.descriptionEt.visibility = View.VISIBLE
                val vrijednost = binding.noActivitySpinner.selectedItem.toString()

                if (vrijednost == "Time Period")
                    binding.unitEt.visibility = View.GONE
                else
                    binding.unitEt.visibility = View.VISIBLE

                if (vrijednost == "Incremental")
                    binding.unitEt.inputType = InputType.TYPE_CLASS_NUMBER
                else
                    binding.unitEt.inputType = InputType.TYPE_CLASS_TEXT

                binding.unitEt.hint = when (vrijednost) {
                    "Incremental" -> "Choose the amount to increment by"
                    "Quantity" -> "Choose custom Quantity Unit"
                    else -> "Choose a custom unit"
                }

            } else {
                binding.unitEt.visibility = View.GONE
                binding.descriptionEt.visibility = View.GONE
            }
        }
        //Ukoliko promijeni aktivnost kategorije, vratiti additional info na unckecked
        binding.noActivitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.addInfoCb.isChecked = false
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.addInfoCb.isChecked = false
            }

        }

        binding.addActivityBtn.setOnClickListener{ uiScope.launch { addCategory()} }

        return binding.root
    }

    private suspend fun addCategory() {
        val state = binding.noActivitySpinner.selectedItem.toString()
        val name = binding.activityNamePt.text.toString()
        val description = binding.descriptionEt.text.toString()
        val unit = binding.unitEt.text.toString()

        if(state == "Time Period"){
            val newTime = TimeTable()
            newTime.name = name
            newTime.description = description
            insertT(newTime)
            delay(1000)
            showToast(requireContext(), "Time period Activity Added")

            timePeriodSingleton.addActivity(TimeCategory(0, name, description,
                    Calendar.getInstance(), mutableListOf(), Calendar.getInstance(), Calendar.getInstance(), 0))

            requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.action_addingActivityFragment_to_navigation_home)

        }else if(state == "Quantity"){
            val newQuantity = QuantityTable()
            newQuantity.name = name
            newQuantity.description = description+ "\nUnit: " + unit
            newQuantity.unit = unit
            insertQ(newQuantity)
            delay(1000)
            showToast(requireContext(), "Quantity Activity Added")

            quantitySingleton.addActivity(QuantityCategory(0, name, description,
                    Calendar.getInstance(), mutableListOf(),0, unit ))

        }else{
            val newIncrement = IncrementTable()
            newIncrement.name = name
            newIncrement.description = description + "\nUnit: " + unit
            newIncrement.increment = unit.toInt()
            insertI(newIncrement)
            delay(1000)
            showToast(requireContext(), "Increment Activity Added")

            incrementSingleton.addActivity(IncrementCategory(0, name, description, Calendar.getInstance(), mutableListOf(), 0, unit.toInt()))
        }
    }

    private suspend fun insertT(timeTable: TimeTable) {

        withContext(Dispatchers.IO) {
            timeDb.insert(timeTable)

        }
    }

    private suspend fun insertI(incrementTable:IncrementTable) {
        withContext(Dispatchers.IO) {
            incrementDb.insert(incrementTable)
        }
    }
    private suspend fun insertQ(quantityTable: QuantityTable) {
        withContext(Dispatchers.IO) {
            quantityDb.insert(quantityTable)

        }
    }



}

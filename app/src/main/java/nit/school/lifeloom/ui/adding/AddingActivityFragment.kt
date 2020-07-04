package nit.school.lifeloom.ui.adding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil.inflate
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import nit.school.lifeloom.MainActivity

import nit.school.lifeloom.R
import nit.school.lifeloom.databinding.FragmentAddingActivityBinding
import nit.school.lifeloom.logic.showToast
import nit.school.lifeloom.singleton.Activity
import nit.school.lifeloom.singleton.activitiesSingleton
import nit.school.lifeloom.ui.adding.adapter.StandardActivitiesAdapter
import nit.school.lifeloom.ui.home.adapter.ActivitiesAdapter

class AddingActivityFragment : Fragment() {

    private lateinit var binding: FragmentAddingActivityBinding
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, R.layout.fragment_adding_activity, container, false)

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
                val vrijednost = binding.noActivitySpinner.selectedItem.toString()
                binding.unitEt.hint = when (vrijednost) {
                    "Time Period" -> "Choose custom Time Period Unit"
                    "Incremental" -> "Choose custom Incremental Unit"
                    "Quantity" -> "Choose custom Quantity Unit"
                    else -> "Choose a custom unit"
                }
                binding.unitEt.visibility = View.VISIBLE
                binding.descriptionEt.visibility = View.VISIBLE
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

        return binding.root
    }

}

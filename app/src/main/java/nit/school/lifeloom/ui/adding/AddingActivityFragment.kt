package nit.school.lifeloom.ui.adding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil.inflate
import nit.school.lifeloom.MainActivity

import nit.school.lifeloom.R
import nit.school.lifeloom.databinding.FragmentAddingActivityBinding
import nit.school.lifeloom.logic.showToast

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
                binding.unitEt.visibility = View.VISIBLE
                binding.descriptionEt.visibility = View.VISIBLE
            } else {
                binding.unitEt.visibility = View.GONE
                binding.descriptionEt.visibility = View.GONE
            }

        }

        return binding.root
    }

}

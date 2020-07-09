package nit.school.lifeloom.ui.list_categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import nit.school.lifeloom.R
import nit.school.lifeloom.singleton.QuantityCategory
import nit.school.lifeloom.singleton.quantitySingleton
import nit.school.lifeloom.ui.list_categories.adapter.MyQuantityRecyclerViewAdapter

class QuantityFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quantity_list, container, false)

        //Vratiti podatke iz baze
        val kategorija_lista = quantitySingleton.getActivities().distinctBy { it?.name }

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Quantity Categories"
        (activity as AppCompatActivity?)!!.supportActionBar!!.subtitle = "Current Categories: " + kategorija_lista.size

        // Set the adapter
        val listQuantityView = view.findViewById<RecyclerView>(R.id.quantity_list)
        if (listQuantityView is RecyclerView) {
            with(listQuantityView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyQuantityRecyclerViewAdapter((kategorija_lista) as List<QuantityCategory>, requireContext(), requireActivity().findNavController(R.id.nav_host_fragment))
            }
        }
        return view
    }
}

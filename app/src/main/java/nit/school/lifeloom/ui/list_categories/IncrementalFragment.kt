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
import nit.school.lifeloom.singleton.IncrementCategory
import nit.school.lifeloom.singleton.incrementSingleton
import nit.school.lifeloom.ui.list_categories.adapter.MyIncrementalRecyclerViewAdapter

import nit.school.lifeloom.ui.list_categories.dummy.DummyContent

class IncrementalFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_incremental_list, container, false)

        //Vratiti podatke iz baze
        val kategorija_lista = incrementSingleton.getActivities().distinctBy { it?.name }

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Incremental Categories"
        (activity as AppCompatActivity?)!!.supportActionBar!!.subtitle = "Current Categories: " + kategorija_lista.size

        // Set the adapter
        val listIncrementalView = view.findViewById<RecyclerView>(R.id.incremental_list)
        if (listIncrementalView is RecyclerView) {
            with(listIncrementalView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyIncrementalRecyclerViewAdapter((kategorija_lista) as List<IncrementCategory>, requireContext(), requireActivity().findNavController(R.id.nav_host_fragment))
            }
        }
        return view
    }

}

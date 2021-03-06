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
import nit.school.lifeloom.singleton.TimeCategory
import nit.school.lifeloom.singleton.timePeriodSingleton
import nit.school.lifeloom.ui.list_categories.adapter.MyTimeRecyclerViewAdapter

class TimeFragment : Fragment() {

    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_time_list, container, false)

        //Vratiti podatke iz baze
        val kategorija_lista = timePeriodSingleton.getActivities().distinctBy { it?.name }

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Time Period Categories"
        (activity as AppCompatActivity?)!!.supportActionBar!!.subtitle = "Current Categories: " + kategorija_lista.size

        // Set the adapter
        val listTimeView = view.findViewById<RecyclerView>(R.id.time_list)
        if (listTimeView is RecyclerView) {
            with(listTimeView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyTimeRecyclerViewAdapter((kategorija_lista) as List<TimeCategory>, requireContext(), requireActivity().findNavController(R.id.nav_host_fragment))
            }
        }
        return view
    }



}

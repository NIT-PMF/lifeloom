package nit.school.lifeloom.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import kotlinx.coroutines.*
import nit.school.lifeloom.MainActivity
import nit.school.lifeloom.R
import nit.school.lifeloom.database.AppDB
import nit.school.lifeloom.database.dao.IncrementCategoryDao
import nit.school.lifeloom.database.dao.QuantityCategoryDao
import nit.school.lifeloom.database.dao.TimeCategoryDao
import nit.school.lifeloom.databinding.FragmentHomeBinding
import nit.school.lifeloom.singleton.*
import nit.school.lifeloom.ui.home.adapter.ActivitiesAdapter
import nit.school.lifeloom.ui.home.adapter.IncrementAdapter
import nit.school.lifeloom.ui.home.adapter.QuantityAdapter
import java.sql.Time

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding


    @InternalCoroutinesApi
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)

        //Dodavanje + ukoliko se vrati sa AddingActivityFragment
        val buttonAdd : ImageButton = (requireActivity() as MainActivity).findViewById(R.id.add_activity_btn)
        buttonAdd.visibility = View.VISIBLE

        //Postavljanje liste
        val quantity = quantitySingleton.getActivities().toList().distinctBy { it?.name }
        val timePeriod = timePeriodSingleton.getActivities().toList().distinctBy { it?.name }
        val increment = incrementSingleton.getActivities().toList().distinctBy { it?.name }

        val quantityList = binding.activitiesView
        val incrementList = binding.incremnetView
        val timePeriodList = binding.timePeriodView


        quantityList.adapter =  QuantityAdapter((quantity as List<QuantityCategory>), requireContext(), requireActivity().findNavController(R.id.nav_host_fragment))
        incrementList.adapter =  IncrementAdapter((increment as List<IncrementCategory>), requireContext(), requireActivity().findNavController(R.id.nav_host_fragment))
        timePeriodList.adapter = ActivitiesAdapter((timePeriod as List<TimeCategory>), requireContext(), requireActivity().findNavController(R.id.nav_host_fragment))

        return binding.root
    }



}

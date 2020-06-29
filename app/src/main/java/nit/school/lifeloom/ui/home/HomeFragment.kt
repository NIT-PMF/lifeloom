package nit.school.lifeloom.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import nit.school.lifeloom.R
import nit.school.lifeloom.databinding.FragmentHomeBinding
import nit.school.lifeloom.singleton.Activity
import nit.school.lifeloom.singleton.activitiesSingleton
import nit.school.lifeloom.ui.home.adapter.ActivitiesAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)

        //Postavljanje liste
        val activities = activitiesSingleton
        val activitiesList = binding.activitiesView
        activitiesList.adapter =  ActivitiesAdapter((activities.getActivities().toList() as List<Activity>))

        return binding.root
    }
}

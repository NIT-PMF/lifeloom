package nit.school.lifeloom.ui.tracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import nit.school.lifeloom.MainActivity
import nit.school.lifeloom.R
import nit.school.lifeloom.databinding.FragmentActivityTrackerBinding

class ActivityTrackerFragment : Fragment() {

    private lateinit var binding: FragmentActivityTrackerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_tracker, container, false)

        //Ovdje vracam naziv aktivnosti, ako zelis jos vratiti provjeri adapter i morat ces dodatni argument u navigation dodati
        val activityName: String = arguments?.getString("activityName") ?: "Not Set"
        binding.activityNameTv.text = activityName
        (activity as MainActivity).supportActionBar?.title = activityName

        return binding.root
    }

}

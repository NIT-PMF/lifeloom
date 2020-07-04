package nit.school.lifeloom.ui.adding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.standard_activity_list_item.view.*
import nit.school.lifeloom.R
import nit.school.lifeloom.logic.showToast
import nit.school.lifeloom.singleton.Activity
import nit.school.lifeloom.ui.adding.adapter.StandardActivitiesAdapter.*


class StandardActivitiesAdapter(private val activitesList: List<Activity>, private val context: Context, private val navController: NavController) :  RecyclerView.Adapter<StandardActivityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandardActivityViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.standard_activity_list_item, parent, false)
        return StandardActivityViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return activitesList.size
    }


    override fun onBindViewHolder(holder: StandardActivityViewHolder, position: Int) {

        holder.name.text = activitesList[position].name
        holder.activityInfo.text = activitesList[position].description
        holder.add_btn.setOnClickListener { view ->
            //val bundle = bundleOf("activityName" to name.text.toString())
            //navController.navigate(R.id.activityTrackerFragment, bundle)
            showToast(context, "DODAVANJE U BAZU")
        }
    }

    //Postavljanje sa layouta
    class StandardActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.standard_name_tv
        val activityInfo = itemView.activity_tv
        val add_btn = itemView.add_standard_btn
    }


    }
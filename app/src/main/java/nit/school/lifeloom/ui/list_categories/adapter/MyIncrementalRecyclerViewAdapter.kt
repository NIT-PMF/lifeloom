package nit.school.lifeloom.ui.list_categories.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import nit.school.lifeloom.R

import kotlinx.android.synthetic.main.fragment_incremental.view.*
import nit.school.lifeloom.logic.showToast
import nit.school.lifeloom.singleton.IncrementCategory

class MyIncrementalRecyclerViewAdapter(
    private val mValues: List<IncrementCategory>,
    private val context: Context,
    private val navController: NavController
) : RecyclerView.Adapter<MyIncrementalRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_incremental, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.name
        holder.mContentView.text = item.description

        var listDates:String = ""
        var listValues:String = ""

        for(element in mValues){
            if(element.name == item.name){
                listDates += listDates + element.date.time.getTime().toString() + ','
                listValues += listValues + element.value + ','
            }
        }


        holder.mButton.setOnClickListener { view ->
            val bundle = bundleOf("activityName" to item.name,
                    Pair("state", "increment"),
                    Pair("dateInSeconds", listDates),
                    Pair("id", item.id.toString()),
                    Pair("properties", item.properties.toString()),
                    Pair("value", listValues),
                    Pair("description", item.description),
                    Pair("increment", item.increment.toString()))
            navController.navigate(R.id.activityTrackerFragment, bundle)
        }

    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val mButton: ImageButton = mView.inspect_btn

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}

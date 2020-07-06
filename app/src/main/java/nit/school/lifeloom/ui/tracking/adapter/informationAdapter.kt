package nit.school.lifeloom.ui.list_categories.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import nit.school.lifeloom.R
import kotlinx.android.synthetic.main.information_list_item.view.*
import nit.school.lifeloom.singleton.IncrementCategory
import nit.school.lifeloom.singleton.QuantityCategory
import nit.school.lifeloom.singleton.TimeCategory


class InformationAdapter(
    private val mValuesIncrement: List<IncrementCategory>,
    private val mValuesQuantity: List<QuantityCategory>,
    private val mValuesTime: List<TimeCategory>,
    private val context: Context,
    private val navController: NavController
) : RecyclerView.Adapter<InformationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.information_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mValuesIncrement.size > 0) {
            val item = mValuesIncrement[position]
            holder.mIdView.text = "OVDJE DATUM VALJDA"
            holder.mContentView.text = item.value.toString()
        } else if (mValuesQuantity.size > 0) {
            val item = mValuesQuantity[position]
            holder.mIdView.text = "OVDJE DATUM VALJDA"
            holder.mContentView.text = item.value.toString()
        } else {
            val item = mValuesTime[position]
            holder.mIdView.text = "OVDJE DATUM VALJDA"
            holder.mContentView.text = item.value.toString()
        }
    }

    override fun getItemCount(): Int {
        return mValuesIncrement.size + mValuesQuantity.size + mValuesTime.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.standard_name_tv
        val mContentView: TextView = mView.activity_tv

    }
}

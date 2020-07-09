package nit.school.lifeloom.ui.list_categories.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.information_list_item.view.*
import nit.school.lifeloom.R
import nit.school.lifeloom.singleton.IncrementCategory
import nit.school.lifeloom.singleton.QuantityCategory
import nit.school.lifeloom.singleton.TimeCategory
import org.w3c.dom.Text
import java.text.SimpleDateFormat


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
        val formatter: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        if (mValuesIncrement.size > 0) {
            val item = mValuesIncrement[position]
            holder.mIdView.text = formatter.format(item.date.time)
            holder.mContentView.text = item.value.toString()
            var propertyName = ""
            for(property in item.properties){
                if(item.value.toInt() > property!!.from.toInt() && item.value.toInt() < property!!.to.toInt()){
                    propertyName = property.name
                }            }
            holder.propertyView.text = propertyName
        } else if (mValuesQuantity.size > 0) {
            val item = mValuesQuantity[position]
            holder.mIdView.text = formatter.format(item.date.time)
            holder.mContentView.text = formatter.format(item.date.time)
            var propertyName = ""
            for(property in item.properties){
                if(item.value.toInt() > property!!.from.toInt() && item.value.toInt() < property!!.to.toInt()){
                    propertyName = property.name
                }
            }
            holder.propertyView.text = propertyName

        } else {
            val item = mValuesTime[position]
            holder.mIdView.text = formatter.format(item.date.time)
            holder.mContentView.text = item.value.toString()
            var propertyName = ""
            for(property in item.properties){
                if(item.value.toInt() > property!!.from.toInt() && item.value.toInt() < property!!.to.toInt()){
                    propertyName = property.name
                }
            }
            holder.propertyView.text = propertyName

        }
    }

    override fun getItemCount(): Int {
        return mValuesIncrement.size + mValuesQuantity.size + mValuesTime.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.standard_name_tv
        val mContentView: TextView = mView.activity_tv
        val propertyView: TextView = mView.property_id

    }
}

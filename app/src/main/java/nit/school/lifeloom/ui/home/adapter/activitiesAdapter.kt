package nit.school.lifeloom.ui.home.adapter
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import nit.school.lifeloom.R
import nit.school.lifeloom.singleton.TimeCategory

class ActivitiesAdapter(private val activitesList: List<TimeCategory>, private val context: Context, private val navController: NavController) : BaseAdapter(){

    override fun getView(position:Int, convertView: View?, parent: ViewGroup?):View{
        // Inflate the custom view
        val inflater = parent?.context?.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_list_item,null)

        // Get the custom view widgets reference
        val name = view.findViewById<TextView>(R.id.activityName)
        val info = view.findViewById<TextView>(R.id.activityInfo)
        val show = view.findViewById<ImageView>(R.id.showActivity_iv)
        val chartBtn = view.findViewById<ImageButton>(R.id.chart_btn)

        //val card = view.findViewById<CardView>(R.id.card_view)

        // Display color name on text view

        name.text = activitesList[position].name
        val diff = activitesList[position].value
        Log.i("messege", diff.toString())
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        info.text = "Last capture : " + hours.toString() + ":" + minutes.toString() + ":" + seconds.toString()



        Log.i("string", activitesList[position].date.time.getTime().toString())
        var listDates:String = ""
        var listValues:String = ""

        for(element in activitesList){
            if(element.name == activitesList[position].name){
                listDates += listDates + element.date.time.getTime().toString() + ','
                listValues += listValues + element.value + ','
            }
        }

        chartBtn.setOnClickListener { view ->
            navController.navigate(R.id.action_navigation_home_to_chartFragment, bundleOf("activity_type" to "time", "category_name" to name.text.toString()))
        }

        show.setOnClickListener { view ->
            val bundle = bundleOf("activityName" to name.text.toString(),
                    Pair("state", "time"),
                    Pair("dateInSeconds", listDates),
                    Pair("id", activitesList[position].id.toString()),
                    Pair("properties", activitesList[position].properties.toString()),
                    Pair("value", listValues),
                    Pair("description", activitesList[position].description))
            navController.navigate(R.id.activityTrackerFragment, bundle)
        }

        // Set background color for card view
        //card.setCardBackgroundColor(list[position].second)

        // Set a click listener for card view
        /*card.setOnClickListener{
            // Show selected color in a toast message
            Toast.makeText(parent.context,
                "Clicked : ${list[position].first}",Toast.LENGTH_SHORT).show()



            // Change the root layout background color
            viewGroup.setBackgroundColor(list[position].second)
        }*/

        return view
    }

    override fun getItem(position: Int): Any {
        return activitesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return activitesList.size
    }
}
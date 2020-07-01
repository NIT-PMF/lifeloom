package nit.school.lifeloom.ui.home.adapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import nit.school.lifeloom.R
import nit.school.lifeloom.logic.showToast
import nit.school.lifeloom.singleton.Activity

class ActivitiesAdapter(private val activitesList: List<Activity>, private val context: Context, private val navController: NavController) : BaseAdapter(){

    override fun getView(position:Int, convertView: View?, parent: ViewGroup?):View{
        // Inflate the custom view
        val inflater = parent?.context?.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_list_item,null)

        // Get the custom view widgets reference
        val name = view.findViewById<TextView>(R.id.activityName)
        val info = view.findViewById<TextView>(R.id.activityInfo)
        val show = view.findViewById<ImageView>(R.id.showActivity_iv)
        //val card = view.findViewById<CardView>(R.id.card_view)

        // Display color name on text view
        name.text = activitesList[position].name
        info.text = activitesList[position].description
        show.setOnClickListener { view ->
            val bundle = bundleOf("activityName" to name.text.toString())
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
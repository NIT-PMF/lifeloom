package nit.school.lifeloom.ui.home.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import nit.school.lifeloom.R
import nit.school.lifeloom.singleton.Activity

/*
//Adapter za listu korisnika unutar Highscore Fragmenta
class ActivitiesAdapter(private val activitesList: List<Activity>) : Adapter<ActivitiesAdapter.ActivitiesAdapterHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesAdapterHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_list_item,
        parent, false)
        return ActivitiesAdapterHolder(itemView)
    }

    override fun onBindViewHolder(holder: ActivitiesAdapterHolder, position: Int) {
        val currentItem = activitesList[position]

        holder.name.text = currentItem.name
    }

    class ActivitiesAdapterHolder(itemView: View) : GridView.ViewHolder(itemView) {
        val name: TextView = itemView.activityName
    }

    override fun getItemCount(): Int {
        return activitesList.size
    }
}*/

class ActivitiesAdapter(private val activitesList: List<Activity>) : BaseAdapter(){

    /*
        **** reference source developer.android.com ***

        View getView (int position, View convertView, ViewGroup parent)
            Get a View that displays the data at the specified position in the data set. You can
            either create a View manually or inflate it from an XML layout file. When the View
            is inflated, the parent View (GridView, ListView...) will apply default layout
            parameters unless you use inflate(int, android.view.ViewGroup, boolean)
            to specify a root view and to prevent attachment to the root.
    */
    override fun getView(position:Int, convertView: View?, parent: ViewGroup?):View{
        // Inflate the custom view
        val inflater = parent?.context?.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_list_item,null)

        // Get the custom view widgets reference
        val name = view.findViewById<TextView>(R.id.activityName)
        val info = view.findViewById<TextView>(R.id.activityInfo)
        //val card = view.findViewById<CardView>(R.id.card_view)

        // Display color name on text view
        name.text = activitesList[position].name
        info.text = activitesList[position].description

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
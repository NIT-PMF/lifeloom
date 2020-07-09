package nit.school.lifeloom.singleton

/** Sadrzi podatke o aktivnostima **/
object activitiesSingleton {
    private var activityList: MutableList<Activity?> = mutableListOf()

    //Konstruktor za aktivnosti
    init {
        activityList.add(
            Activity(
                0,
                "Trcanje",
                "Otrcao si 0 km danas")
        )
        activityList.add(
            Activity(
                1,
                "Skakanje",
                "Skocio si 0x danas"
            )
        )
        activityList.add(
            Activity(
                1,
                "Spavanje",
                "Spavao si 5 sati"
            )
        )
        activityList.add(
            Activity(
                1,
                "Tecnost",
                "Popio si 2L vode"
            )
        )
    }


    //Vracanje liste s podacima
    fun getActivities(): MutableList<Activity?> {
        return activityList
    }

    fun findActivity(name: String): Activity? {
        return activityList.find {
                activity -> activity?.name.equals(name)
        }
    }

    //Vracanje aktivnosti po ID-u
    fun getActivityById(ActivityId: Number): Activity? {
        return activityList.find { it?.id == ActivityId }
    }

    //Ukupan Broj Aktivnosti
    fun getActivitySize(): Number {
        return activityList.size
    }

    //Dodaj Novu Aktivnosti
    fun addActivity(Activity: Activity) {
        activityList.add(Activity)
    }


}

data class Activity(val id: Number, val name: String, val description: String)
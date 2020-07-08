package nit.school.lifeloom.database.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "time_category")
data class TimeTable(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,

        @ColumnInfo(name = "name")
        var name: String = "",

        @ColumnInfo(name = "description")
        var description: String = "",

        @ColumnInfo(name = "date")
        var date: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "properties")
        var properties: String = "",

        @ColumnInfo(name = "startTime")
        val startTime: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "endTime")
        var endTime: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "value")
        var value: Long = System.currentTimeMillis()



        )
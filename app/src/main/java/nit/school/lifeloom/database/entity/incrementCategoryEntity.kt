package nit.school.lifeloom.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.FileDescriptor

@Entity(tableName = "increment_category")
data class IncrementTable(
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

        @ColumnInfo(name = "value")
        var value: Int = 0,

        @ColumnInfo(name = "increment")
        var increment: Int = 0

)
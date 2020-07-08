package nit.school.lifeloom.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import nit.school.lifeloom.database.entity.TimeTable
import nit.school.lifeloom.singleton.TimeCategory

@Dao
interface TimeCategoryDao {

    @Insert
    suspend fun insert(timeTable: TimeTable)

    @Query("Update time_category Set value = :value, startTime= :startTime, endTime= :endTime where name = :name and date = :date")
    fun update(value:Long, startTime: Long, endTime: Long, name: String, date: Long)

    @Query("Delete from time_category where name = :key")
    fun delete(key: String)

    @Query("Select * from time_category order by date desc")
    fun getAll(): List<TimeTable>

}
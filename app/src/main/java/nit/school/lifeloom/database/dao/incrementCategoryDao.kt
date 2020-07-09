package nit.school.lifeloom.database.dao

import androidx.room.*
import nit.school.lifeloom.database.entity.IncrementTable
import nit.school.lifeloom.database.entity.TimeTable
import nit.school.lifeloom.singleton.IncrementCategory
import nit.school.lifeloom.singleton.TimeCategory

@Dao
interface IncrementCategoryDao {

    @Insert
    suspend fun insert(incrementTable: IncrementTable)


    @Query("Update increment_category Set value =:value where name =:name and date =:date")
    suspend fun update(value: Int, name: String, date:Long)

    @Query("Delete from increment_category where name = :key")
    fun delete(key: String)

    @Query("Update increment_category Set properties = properties + :value where name = :name")
    fun updateProperty(value:String, name:String)
    @Query("Select * from increment_category order by date desc")
    fun getAll(): List<IncrementTable>

}
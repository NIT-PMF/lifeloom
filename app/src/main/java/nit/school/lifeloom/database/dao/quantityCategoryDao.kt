package nit.school.lifeloom.database.dao

import androidx.room.*
import nit.school.lifeloom.database.entity.IncrementTable
import nit.school.lifeloom.database.entity.QuantityTable
import nit.school.lifeloom.database.entity.TimeTable
import nit.school.lifeloom.singleton.QuantityCategory
import nit.school.lifeloom.singleton.TimeCategory

@Dao
interface QuantityCategoryDao {

    @Insert
    suspend fun insert(quantityTable: QuantityTable)

    @Query("Update quantity_category Set value =:value where name =:name and date =:date")
    suspend fun update(value: Int, name: String, date:Long)

    @Query("Delete from quantity_category where name = :key")
    fun delete(key: String)

    @Query("Update quantity_category Set properties = properties + :value where name = :name")
    fun updateProperty(value:String, name:String)

    @Query("Select * from quantity_category order by date desc")
    fun getAll(): List<QuantityTable>

}
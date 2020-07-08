package nit.school.lifeloom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import nit.school.lifeloom.database.dao.IncrementCategoryDao
import nit.school.lifeloom.database.dao.QuantityCategoryDao
import nit.school.lifeloom.database.dao.TimeCategoryDao
import nit.school.lifeloom.database.entity.IncrementTable
import nit.school.lifeloom.database.entity.QuantityTable
import nit.school.lifeloom.database.entity.TimeTable

@Database(entities = [TimeTable::class, IncrementTable::class, QuantityTable::class], version = 1, exportSchema = false)
abstract  class AppDB: RoomDatabase(){


    abstract val  incrementCategoryDao: IncrementCategoryDao
    abstract val  timeCategoryDao: TimeCategoryDao
    abstract val  quantityCategoryDao: QuantityCategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        @InternalCoroutinesApi
        fun getInstance(context: Context): AppDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDB::class.java,
                            "Lifeloom datebase"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

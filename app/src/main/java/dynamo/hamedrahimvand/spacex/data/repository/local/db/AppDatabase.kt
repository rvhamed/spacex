package dynamo.hamedrahimvand.spacex.data.repository.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dynamo.hamedrahimvand.spacex.data.model.db_models.LaunchesEntity

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
@Database(entities = [LaunchesEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun launchesDao(): LaunchesDao

    companion object {
        fun getInstance(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "AppDatabase"
        ).build()
    }
}
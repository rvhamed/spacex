package dynamo.hamedrahimvand.spacex.data.repository.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dynamo.hamedrahimvand.spacex.data.model.Converters
import dynamo.hamedrahimvand.spacex.data.model.Launch
import dynamo.hamedrahimvand.spacex.data.model.LinksTypeConverter
import dynamo.hamedrahimvand.spacex.data.model.PatchTypeConverter

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
@Database(entities = [Launch::class], version = 1)
@TypeConverters(Converters::class, LinksTypeConverter::class, PatchTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun launchesDao(): LaunchesDao

    companion object {
        fun getInstance(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "AppDatabase"
        ).build()
    }
}
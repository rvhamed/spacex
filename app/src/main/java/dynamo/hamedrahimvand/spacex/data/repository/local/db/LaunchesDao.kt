package dynamo.hamedrahimvand.spacex.data.repository.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dynamo.hamedrahimvand.spacex.data.model.Launch
import kotlinx.coroutines.flow.Flow

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
@Dao
interface LaunchesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(launchesList: List<Launch>)

    @Query("SELECT * FROM launch")
    fun loadAllLaunches(): Flow<List<Launch>>

    @Query("DELETE FROM launch")
    suspend fun deleteExpiredLaunches()

}

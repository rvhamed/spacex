package dynamo.hamedrahimvand.spacex.data.repository.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dynamo.hamedrahimvand.spacex.data.model.db_models.LaunchesEntity
import kotlinx.coroutines.flow.Flow

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
@Dao
interface LaunchesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(launchesList: List<LaunchesEntity>)

    @Query("SELECT * FROM LaunchesEntity")
    fun loadAllLaunches(): Flow<List<LaunchesEntity>>

    @Query("DELETE FROM LaunchesEntity")
    suspend fun deleteExpiredLaunches()

}

package dynamo.hamedrahimvand.spacex.data.repository.local

import dynamo.hamedrahimvand.spacex.data.model.db_models.LaunchesEntity
import kotlinx.coroutines.flow.Flow

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
interface LocalDataSource {
    fun loadLaunches(): Flow<List<LaunchesEntity>>
    fun insertLaunches(launches: List<LaunchesEntity>)
}
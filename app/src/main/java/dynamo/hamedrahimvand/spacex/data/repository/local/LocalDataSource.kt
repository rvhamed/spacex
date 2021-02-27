package dynamo.hamedrahimvand.spacex.data.repository.local

import dynamo.hamedrahimvand.spacex.data.model.Launch
import kotlinx.coroutines.flow.Flow

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
interface LocalDataSource {
    fun loadLaunches(): Flow<List<Launch>>
    suspend fun insertLaunches(launches: List<Launch>)
    suspend fun deleteExpiredLaunches()
}
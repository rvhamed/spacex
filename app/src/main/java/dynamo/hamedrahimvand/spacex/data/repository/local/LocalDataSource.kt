package dynamo.hamedrahimvand.spacex.data.repository.local

import dynamo.hamedrahimvand.spacex.data.model.local_models.Launches
import kotlinx.coroutines.flow.Flow

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
interface LocalDataSource {
    fun loadLaunches(): Flow<List<Launches>>
    fun insertLaunches()
}
package dynamo.hamedrahimvand.spacex.data.repository.local

import dynamo.hamedrahimvand.spacex.data.model.local_models.Launches
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
class LocalDataSourceImpl @Inject constructor() : LocalDataSource {
    override fun loadLaunches(): Flow<List<Launches>> {
        TODO("Not yet implemented")
    }

    override fun insertLaunches() {
        TODO("Not yet implemented")
    }
}
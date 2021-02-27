package dynamo.hamedrahimvand.spacex.data.repository.local

import dynamo.hamedrahimvand.spacex.data.model.Launch
import dynamo.hamedrahimvand.spacex.data.repository.local.db.LaunchesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
class LocalDataSourceImpl @Inject constructor(
    private val launchesDao: LaunchesDao
) : LocalDataSource {

    override fun loadLaunches(): Flow<List<Launch>> = launchesDao.loadAllLaunches()
    override suspend fun insertLaunches(launches: List<Launch>) = launchesDao.insert(launches)
    override suspend fun deleteExpiredLaunches() = launchesDao.deleteExpiredLaunches()
}
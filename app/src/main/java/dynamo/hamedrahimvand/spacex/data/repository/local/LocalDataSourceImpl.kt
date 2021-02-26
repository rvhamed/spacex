package dynamo.hamedrahimvand.spacex.data.repository.local

import dynamo.hamedrahimvand.spacex.data.model.db_models.LaunchesEntity
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

    override fun loadLaunches(): Flow<List<LaunchesEntity>> = launchesDao.loadAllLaunches()
    override suspend fun insertLaunches(launches: List<LaunchesEntity>) = launchesDao.insert(launches)
    override suspend fun deleteExpiredLaunches() = launchesDao.deleteExpiredLaunches()
}
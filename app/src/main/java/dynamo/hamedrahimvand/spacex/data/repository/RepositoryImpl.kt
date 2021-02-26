package dynamo.hamedrahimvand.spacex.data.repository

import dynamo.hamedrahimvand.spacex.data.repository.local.LocalDataSource
import dynamo.hamedrahimvand.spacex.data.repository.remote.CloudDataSource
import javax.inject.Inject

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val cloudDataSource: CloudDataSource
) : Repository {


    override suspend fun loadLaunchesAsync() =
        cloudDataSource.loadLaunches()


}
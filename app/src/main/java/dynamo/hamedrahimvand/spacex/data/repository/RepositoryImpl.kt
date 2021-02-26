package dynamo.hamedrahimvand.spacex.data.repository

import dynamo.hamedrahimvand.spacex.data.model.local_models.Launches
import dynamo.hamedrahimvand.spacex.data.model.response_models.LaunchesResponse
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import dynamo.hamedrahimvand.spacex.data.repository.local.LocalDataSource
import dynamo.hamedrahimvand.spacex.data.repository.remote.CloudDataSource
import kotlinx.coroutines.flow.Flow
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

    override suspend fun loadLaunches(): Flow<Resource<List<Launches>>> {
        return object : NetworkBoundResource<List<Launches>, List<LaunchesResponse>>() {
            override fun saveCallResult(item: Resource<List<LaunchesResponse>?>) =
                localDataSource.insertLaunches()

            override fun shouldFetch(data: List<Launches>?): Boolean = data.isNullOrEmpty()

            override suspend fun loadFromDb(): Flow<List<Launches>> = localDataSource.loadLaunches()

            override suspend fun createCall(): Flow<Resource<List<LaunchesResponse>>> =
                loadLaunchesAsync()

        }.asFlow()
    }

    override suspend fun loadLaunchesAsync() =
        cloudDataSource.loadLaunches()


}
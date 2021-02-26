package dynamo.hamedrahimvand.spacex.data.repository

import dynamo.hamedrahimvand.spacex.data.model.db_models.LaunchesEntity
import dynamo.hamedrahimvand.spacex.data.model.mapper.toLaunchesEntity
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

    override suspend fun loadLaunches(isForceFetch: Boolean): Flow<Resource<List<LaunchesEntity>>> {
        return object : NetworkBoundResource<List<LaunchesEntity>, List<LaunchesResponse>>() {
            override fun saveCallResult(item: Resource<List<LaunchesResponse>?>) {
                item.data?.let { launchesResponseList ->
                    val entityList = launchesResponseList.map {
                        it.toLaunchesEntity()
                    }
                    localDataSource.insertLaunches(entityList)
                }
            }

            override fun shouldFetch(data: List<LaunchesEntity>?): Boolean =
                data.isNullOrEmpty() || isForceFetch

            override suspend fun loadFromDb(): Flow<List<LaunchesEntity>> =
                localDataSource.loadLaunches()

            override suspend fun createCall(): Flow<Resource<List<LaunchesResponse>>> =
                loadLaunchesAsync()

        }.asFlow()
    }

    override suspend fun loadLaunchesAsync() =
        cloudDataSource.loadLaunches()


}
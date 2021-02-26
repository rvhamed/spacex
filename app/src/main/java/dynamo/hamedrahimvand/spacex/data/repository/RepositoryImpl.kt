package dynamo.hamedrahimvand.spacex.data.repository

import dynamo.hamedrahimvand.spacex.data.model.db_models.LaunchesEntity
import dynamo.hamedrahimvand.spacex.data.model.mapper.toLaunchesEntity
import dynamo.hamedrahimvand.spacex.data.model.request_models.LaunchesRequestModel
import dynamo.hamedrahimvand.spacex.data.model.response_models.LaunchesResponse
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import dynamo.hamedrahimvand.spacex.data.repository.local.LocalDataSource
import dynamo.hamedrahimvand.spacex.data.repository.local.db.AppPreferences
import dynamo.hamedrahimvand.spacex.data.repository.remote.CloudDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val cloudDataSource: CloudDataSource,
    private val appPreferences: AppPreferences,
) : Repository {

    override suspend fun loadLaunches(
        isRefresh:Boolean,
        isForceFetch: Boolean,
        requestModel: LaunchesRequestModel
    ): Flow<Resource<List<LaunchesEntity>>> {
        return object : NetworkBoundResource<List<LaunchesEntity>, LaunchesResponse>() {
            override suspend fun saveCallResult(item: Resource<LaunchesResponse?>) {
                item.data?.let { launchesResponse ->
                    if(isRefresh){
                        deleteExpiredLaunches()
                    }
                    appPreferences.setHasNextPage(launchesResponse.hasNextPage ?: false)
                    if (launchesResponse.hasNextPage == true) {
                        appPreferences.setLastPage(launchesResponse.page)
                    }
                    val entityList = launchesResponse.launches.map {
                        it.toLaunchesEntity()
                    }
                    localDataSource.insertLaunches(entityList)
                }
            }

            override fun shouldFetch(data: List<LaunchesEntity>?): Boolean =
                data.isNullOrEmpty() || isForceFetch

            override suspend fun loadFromDb(): Flow<List<LaunchesEntity>> =
                localDataSource.loadLaunches()

            override suspend fun createCall(): Flow<Resource<LaunchesResponse>> =
                loadLaunchesAsync(requestModel)

        }.asFlow()
    }

    override suspend fun loadLaunchesAsync(launchesRequestModel: LaunchesRequestModel) =
        cloudDataSource.loadLaunches(launchesRequestModel)

    override suspend fun deleteExpiredLaunches() = localDataSource.deleteExpiredLaunches()
}
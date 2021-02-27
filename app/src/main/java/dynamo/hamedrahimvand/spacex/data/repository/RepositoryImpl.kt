package dynamo.hamedrahimvand.spacex.data.repository

import dynamo.hamedrahimvand.spacex.data.model.Launch
import dynamo.hamedrahimvand.spacex.data.model.request_models.LaunchesRequestModel
import dynamo.hamedrahimvand.spacex.data.model.LaunchResponse
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import dynamo.hamedrahimvand.spacex.data.repository.local.LocalDataSource
import dynamo.hamedrahimvand.spacex.data.repository.local.db.AppPreferences
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
    private val cloudDataSource: CloudDataSource,
    private val appPreferences: AppPreferences,
) : Repository {

    override suspend fun loadLaunches(
        isRefresh:Boolean,
        isForceFetch: Boolean,
        requestModel: LaunchesRequestModel
    ): Flow<Resource<List<Launch>>> {
        return object : NetworkBoundResource<List<Launch>, LaunchResponse>() {
            override suspend fun saveCallResult(item: Resource<LaunchResponse?>) {
                item.data?.let { launchesResponse ->
                    if(isRefresh){
                        deleteExpiredLaunches()
                    }
                    appPreferences.setHasNextPage(launchesResponse.hasNextPage ?: false)
                    if (launchesResponse.hasNextPage == true) {
                        appPreferences.setLastPage(launchesResponse.page)
                    }
                    localDataSource.insertLaunches(launchesResponse.launches)
                }
            }

            override fun shouldFetch(data: List<Launch>?): Boolean =
                data.isNullOrEmpty() || isForceFetch || isRefresh

            override suspend fun loadFromDb(): Flow<List<Launch>> =
                localDataSource.loadLaunches()

            override suspend fun createCall(): Flow<Resource<LaunchResponse>> =
                loadLaunchesAsync(requestModel)

        }.asFlow()
    }

    override suspend fun loadLaunchesAsync(launchesRequestModel: LaunchesRequestModel) =
        cloudDataSource.loadLaunches(launchesRequestModel)

    override suspend fun deleteExpiredLaunches() = localDataSource.deleteExpiredLaunches()
}
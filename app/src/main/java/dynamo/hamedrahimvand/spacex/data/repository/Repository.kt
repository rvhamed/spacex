package dynamo.hamedrahimvand.spacex.data.repository

import dynamo.hamedrahimvand.spacex.data.model.db_models.LaunchesEntity
import dynamo.hamedrahimvand.spacex.data.model.request_models.LaunchesRequestModel
import dynamo.hamedrahimvand.spacex.data.model.response_models.LaunchesResponse
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import kotlinx.coroutines.flow.Flow

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
interface Repository {

    suspend fun loadLaunchesAsync(launchesRequestModel: LaunchesRequestModel): Flow<Resource<LaunchesResponse>>
    suspend fun loadLaunches(isRefresh:Boolean, isForceFetch: Boolean, requestModel: LaunchesRequestModel): Flow<Resource<List<LaunchesEntity>>>
    suspend fun deleteExpiredLaunches()
}
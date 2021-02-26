package dynamo.hamedrahimvand.spacex.data.repository.remote

import dynamo.hamedrahimvand.spacex.data.model.request_models.LaunchesRequestModel
import dynamo.hamedrahimvand.spacex.data.model.response_models.LaunchesResponse
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import kotlinx.coroutines.flow.Flow

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
interface CloudDataSource {
    suspend fun loadLaunches(launchesRequestModel: LaunchesRequestModel): Flow<Resource<LaunchesResponse>>
}
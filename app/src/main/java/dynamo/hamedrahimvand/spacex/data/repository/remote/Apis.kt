package dynamo.hamedrahimvand.spacex.data.repository.remote

import dynamo.hamedrahimvand.spacex.data.model.request_models.LaunchesRequestModel
import dynamo.hamedrahimvand.spacex.data.model.LaunchResponse
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
interface Apis {

    @POST("launches/query")
    fun loadLaunches(@Body launchesRequestModel: LaunchesRequestModel): Flow<Resource<LaunchResponse>>

}
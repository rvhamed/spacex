package dynamo.hamedrahimvand.spacex.data.repository.remote

import dynamo.hamedrahimvand.spacex.data.model.response_models.LaunchesResponse
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
interface Apis {

    @GET("launches")
    fun loadLaunches(): Flow<Resource<List<LaunchesResponse>>>

}
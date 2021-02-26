package dynamo.hamedrahimvand.spacex.data.repository

import dynamo.hamedrahimvand.spacex.data.model.local_models.Launches
import dynamo.hamedrahimvand.spacex.data.model.response_models.LaunchesResponse
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import kotlinx.coroutines.flow.Flow

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
interface Repository{

    suspend fun loadLaunchesAsync(): Flow<Resource<List<LaunchesResponse>>>
    suspend fun loadLaunches(): Flow<Resource<List<Launches>>>
}
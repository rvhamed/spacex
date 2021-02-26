package dynamo.hamedrahimvand.spacex.data.repository.remote

import dynamo.hamedrahimvand.spacex.data.model.request_models.LaunchesRequestModel
import javax.inject.Inject

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
class CloudDataSourceImpl @Inject constructor(private val apis: Apis) : CloudDataSource {
    override suspend fun loadLaunches(launchesRequestModel: LaunchesRequestModel) = apis.loadLaunches(launchesRequestModel)
}
package dynamo.hamedrahimvand.spacex.data.model.mapper

import dynamo.hamedrahimvand.spacex.data.model.db_models.LaunchesEntity
import dynamo.hamedrahimvand.spacex.data.model.response_models.LaunchesResponse

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
fun LaunchesResponse.toLaunchesEntity(): LaunchesEntity {
    return LaunchesEntity(this.id, this.name)
}
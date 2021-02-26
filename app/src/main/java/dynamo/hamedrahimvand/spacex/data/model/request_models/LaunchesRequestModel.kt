package dynamo.hamedrahimvand.spacex.data.model.request_models

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
data class LaunchesRequestModel(
    val options: Options
)

data class Options(
    val limit: Int,
    val page: Int
)
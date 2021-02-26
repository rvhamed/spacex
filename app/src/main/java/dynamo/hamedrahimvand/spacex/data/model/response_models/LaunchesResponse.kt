package dynamo.hamedrahimvand.spacex.data.model.response_models

import com.google.gson.annotations.SerializedName

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
//TODO This current model is temporary, is just for test. This should be completed.
// Also, pagination is not handled currently.
data class LaunchesResponse(
    @SerializedName("docs")
    val launches: List<LaunchDetailsResponse>,
    var page: Int?,
    var nextPage: Int?,
    var prevPage: Int?,
    var totalPages: Int?,
    var totalCount: Int?,
    var hasNextPage: Boolean?
)

data class LaunchDetailsResponse(
    val id: String,
    val name: String?,
    val links: Links?
)

data class Links(
    val patch: Patch?
)

data class Patch(
    val small: String?,
    val large: String?
)
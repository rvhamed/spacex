package dynamo.hamedrahimvand.spacex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
data class LaunchResponse(
    @SerializedName("docs")
    val launches: List<Launch>,
    var page: Int?,
    var nextPage: Int?,
    var prevPage: Int?,
    var totalPages: Int?,
    var totalCount: Int?,
    var hasNextPage: Boolean?
)

@Entity
data class Launch(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String?,
    val links: Links?,
    @SerializedName("date_utc")
    val date: Date
)

data class Links(
    val patch: Patch?
)

data class Patch(
    val small: String?,
    val large: String?
)
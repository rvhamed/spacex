package dynamo.hamedrahimvand.spacex.data.model.db_models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
@Entity(tableName = "LaunchesEntity")
data class LaunchesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String?,
    val smallIcon: String?
)
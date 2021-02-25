package dynamo.hamedrahimvand.spacex.data.repository.remote

import javax.inject.Inject

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
class CloudDataSourceImpl @Inject constructor(private val apis: Apis) : CloudDataSource {
    override fun loadNothing(): String {
        return ""
    }
}
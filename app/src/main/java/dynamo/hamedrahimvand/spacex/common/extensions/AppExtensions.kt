package dynamo.hamedrahimvand.spacex.common.extensions

import android.util.Log
import dynamo.hamedrahimvand.spacex.BuildConfig

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
const val APP_TAG = "SpaceTag"
fun <T : Any?> T.log(description: String? = null): T {

    if (!BuildConfig.DEBUG) {
        return this
    }

    if (description.isNullOrBlank()) {
        Log.d(APP_TAG, "$this")
    } else {
        Log.d(APP_TAG, "$description ---> $this")
    }
    return this
}
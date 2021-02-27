package dynamo.hamedrahimvand.spacex.common.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/27/21
 */
const val DAY_DATE_PATTERN = "EEEE, dd MMMM"

fun Date?.toFormattedStringUTC(pattern: String, isUtc: Boolean = true): String? {
    if (this == null) {
        return null
    }
    return try {
        val simpleDateFormat = SimpleDateFormat(pattern)
        if (isUtc)
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        simpleDateFormat.format(this)
    } catch (e: Exception) {
        null
    }
}
package dynamo.hamedrahimvand.spacex.data.repository.local.db

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */

@Singleton
class AppPreferences @Inject constructor(private val sharePref: SharedPreferences) {

    private val LAST_PAGE_KEY = "LAST_PAGE"
    private val HAS_NEXT_PAGE_KEY = "HAS_NEXT_PAGE"

    fun setHasNextPage(hasNextPage:Boolean) {
        sharePref.edit().putBoolean(HAS_NEXT_PAGE_KEY,hasNextPage).apply()
    }

    fun getHasNextPage() = sharePref.getBoolean(HAS_NEXT_PAGE_KEY, false)

    fun setLastPage(page: Int?) {
        sharePref.edit().putInt(LAST_PAGE_KEY, page ?: - 1).apply()
    }

    fun getLastPage() =
        sharePref.getInt(LAST_PAGE_KEY, 1)


}
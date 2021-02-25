package dynamo.hamedrahimvand.spacex.common.base

import androidx.navigation.NavDirections

/**
 *  With Navigator interface we can navigates between fragments without finding controller handy.
 *  It implemented in [BaseActivity] to handle navigation.
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
interface Navigator {
    fun navigateTo(navDirections: NavDirections)
}
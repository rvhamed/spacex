package dynamo.hamedrahimvand.spacex.data.model.error_model

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
class ErrorModel(
    val errorCode: Int,
    var errorMessage: String?,
) {
    fun get(): String {
        return errorMessage ?: "No message"
    }
}
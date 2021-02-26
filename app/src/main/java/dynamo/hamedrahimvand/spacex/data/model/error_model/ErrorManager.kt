package dynamo.hamedrahimvand.spacex.data.model.error_model

import dynamo.hamedrahimvand.spacex.BuildConfig
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
class ErrorManager {

    object ErrorCodes {
        const val ERROR_EMPTY = 1
        const val ERROR_GENERAL = 400
        const val ERROR_NETWORK = 404
        const val ERROR_INTERNAL_SERVER = 500
    }

    object ErrorMessage {
        const val NO_INTERNET_CONNECTION = "No Internet connection"
        const val INTERNAL_SERVER_ERROR = "Internal server error."
        const val CONNECTION_ERROR = "Connection error!"
        const val UNKNOWN_ERROR_OCCURRED = "Unknown error occurred"
        const val EMPTY_ERROR = "Fatality Error (Empty)"
    }

    companion object {

        fun handleError(e: Throwable?): ErrorModel {

            if (e == null) {
                return ErrorModel(
                    ErrorCodes.ERROR_GENERAL,
                    ErrorMessage.UNKNOWN_ERROR_OCCURRED
                )
            }

            val errorModel: ErrorModel = when (e) {
                is HttpException -> getErrorModelOnHttpExceptionOccurred(e)
                is ConnectException -> getInternetConnectionErrorModel()
                is Resource.EmptyException -> getEmptyError(e)
                else -> getGeneralError()
            }
            e.printStackTrace()
            return errorModel
        }


        private fun getErrorModelOnHttpExceptionOccurred(
            e: HttpException
        ): ErrorModel {

            val response = e.response()
            val errorCode = response?.code()
            val errorMessage = StringBuilder()

            var errorJsonObject = JSONObject()
            try {
                errorJsonObject = JSONObject(response?.errorBody()?.string())
                val errorsArray: JSONArray =
                    errorJsonObject.getJSONObject("errors").getJSONArray("base")
                if (errorsArray.length() == 1)
                    errorMessage.append(errorsArray.getString(0))
                else
                    for (i in 0 until errorsArray.length()) {
                        errorMessage.append("${errorsArray.getString(i)}\n")
                    }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
            }

            return getErrorModelOfErrorCode(errorCode, errorMessage.toString(), errorJsonObject)
        }

        private fun getErrorModelOfErrorCode(
            errorCode: Int?,
            errorMessage: String?,
            errorJsonObject: JSONObject
        ): ErrorModel {
            var result = errorMessage

            when (errorCode) {
                ErrorCodes.ERROR_INTERNAL_SERVER -> {
                    result = ErrorMessage.INTERNAL_SERVER_ERROR
                }
                else -> {
                    if (result.isNullOrBlank()) {
                        result = ErrorMessage.CONNECTION_ERROR
                    }
                }
            }
            return ErrorModel(
                errorCode ?: ErrorCodes.ERROR_NETWORK,
                result
            )
        }

        private fun getEmptyError(e: Resource.EmptyException): ErrorModel {
            return ErrorModel(
                ErrorCodes.ERROR_EMPTY,
                ErrorMessage.EMPTY_ERROR
            )
        }

        private fun getGeneralError(): ErrorModel {
            return ErrorModel(
                ErrorCodes.ERROR_GENERAL,
                ErrorMessage.CONNECTION_ERROR
            )
        }

        private fun getInternetConnectionErrorModel(): ErrorModel {
            return ErrorModel(
                ErrorCodes.ERROR_GENERAL,
                ErrorMessage.NO_INTERNET_CONNECTION
            )
        }
    }

}
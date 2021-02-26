package dynamo.hamedrahimvand.spacex.data.model.retrofit

import dynamo.hamedrahimvand.spacex.data.model.error_model.ErrorModel
import java.lang.Exception

data class Resource<out T>(val status: Status, val data: T?, val error: Throwable?) {
    var errorModel : ErrorModel? = null

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: Throwable?, data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                error
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    class EmptyException: Exception()
}
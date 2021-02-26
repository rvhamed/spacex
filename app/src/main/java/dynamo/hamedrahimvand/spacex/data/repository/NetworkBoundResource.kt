package dynamo.hamedrahimvand.spacex.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import dynamo.hamedrahimvand.spacex.data.model.error_model.ErrorManager
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource.Status.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
abstract class NetworkBoundResource<ResultType, RequestType>() {
    private val jobMaps = mutableMapOf<String, Job>()

    fun asFlow() = flow {
        emit(Resource.loading())

        val dbValue = loadFromDb().first()
        if (shouldFetch(dbValue)) {
            emit(Resource.loading(dbValue))
            createCall()
                .catch { error ->
                    emit(Resource.error(error, null))
                }
                .flowOn(Dispatchers.IO)
                .collect { resource ->
                    when (resource.status) {
                        SUCCESS -> {
                            saveCallResult(resource)
                            emitAll(loadFromDb().map { Resource.success(it) })
                        }
                        ERROR -> {
                            val handledError = ErrorManager.handleError(resource.error)
                            if (handledError.errorCode != ErrorManager.ErrorCodes.ERROR_EMPTY) {
                                resource.apply {
                                    resource.errorModel = handledError
                                }
                            }
                            onFetchFailed()
                            emitAll(loadFromDb().map { Resource.error(resource.error, it) })
                        }
                    }
                }
        } else {
            emitAll(loadFromDb().map { Resource.success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    @WorkerThread
    protected abstract fun saveCallResult(item: Resource<RequestType?>)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): Flow<ResultType?>

    @MainThread
    protected abstract suspend fun createCall(): Flow<Resource<RequestType?>>
}
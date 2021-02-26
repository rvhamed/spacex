package dynamo.hamedrahimvand.spacex.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dynamo.hamedrahimvand.spacex.common.livedata.EventLiveData
import dynamo.hamedrahimvand.spacex.data.model.error_model.ErrorManager
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import dynamo.hamedrahimvand.spacex.data.usecase.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.collections.set


/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
abstract class BaseViewModel : ViewModel() {
    private val jobMaps = mutableMapOf<String, Job>()

    fun <T> loadData(baseUseCase: BaseUseCase<T>, liveData: EventLiveData<Resource<T>>) {
        val job = viewModelScope.launch {
            baseUseCase.loadData()
                .catch { error ->
                    emit(Resource.error(error, null))
                }
                .flowOn(Dispatchers.IO)
                .collect { resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            liveData.postValue(resource)
                        }
                        Resource.Status.ERROR -> {
                            val handledError = ErrorManager.handleError(resource.error)
                            if (handledError.errorCode != ErrorManager.ErrorCodes.ERROR_EMPTY) {
                                resource.apply {
                                    resource.errorModel = handledError
                                }
                            }
                            liveData.postValue(resource)
                        }
                    }
                }
        }

        cancelJob(baseUseCase)
        jobMaps[baseUseCase.hashCode().toString()] = job
    }

    private fun <T> cancelJob(baseUseCase: BaseUseCase<T>) {
        val className = baseUseCase.hashCode().toString()
        if (jobMaps.keys.contains(className)) {
            jobMaps[className]?.cancel()
        }
    }

}
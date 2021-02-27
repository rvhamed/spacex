package dynamo.hamedrahimvand.spacex.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dynamo.hamedrahimvand.spacex.common.livedata.EventLiveData
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import dynamo.hamedrahimvand.spacex.data.usecase.BaseUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
abstract class BaseViewModel : ViewModel() {
    private val jobMaps = mutableMapOf<String, Job>()

    fun <T> loadData(baseUseCase: BaseUseCase<T>, liveData: EventLiveData<Resource<T>>) {
        val job = viewModelScope.launch {
            baseUseCase.loadData().collect{
                liveData.value = it
            }
        }
        cancelJob(baseUseCase)
        jobMaps[baseUseCase.hashCode().toString()] = job
    }

    private fun <T>cancelJob(baseUseCase: BaseUseCase<T>){
        val className = baseUseCase.hashCode().toString()
        if (jobMaps.keys.contains(className)){
            jobMaps[className]?.cancel()
        }
    }
}
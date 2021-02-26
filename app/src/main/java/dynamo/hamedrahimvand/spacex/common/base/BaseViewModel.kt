package dynamo.hamedrahimvand.spacex.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dynamo.hamedrahimvand.spacex.common.livedata.EventLiveData
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import dynamo.hamedrahimvand.spacex.data.usecase.BaseUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
abstract class BaseViewModel : ViewModel() {

    fun <T> loadData(baseUseCase: BaseUseCase<T>, liveData: EventLiveData<Resource<T>>) {
        viewModelScope.launch {
            baseUseCase.loadData().collectLatest {
                liveData.value = it
            }
        }
    }

}
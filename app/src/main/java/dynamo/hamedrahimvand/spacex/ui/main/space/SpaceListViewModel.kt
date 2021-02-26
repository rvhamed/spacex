package dynamo.hamedrahimvand.spacex.ui.main.space

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamo.hamedrahimvand.spacex.common.base.BaseViewModel
import dynamo.hamedrahimvand.spacex.common.livedata.EventLiveData
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import dynamo.hamedrahimvand.spacex.data.model.ui_models.Launches
import dynamo.hamedrahimvand.spacex.data.usecase.LoadLaunchesUseCase
import javax.inject.Inject

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
@HiltViewModel
class SpaceListViewModel @Inject constructor(private val loadLaunchesUseCase: LoadLaunchesUseCase) :
    BaseViewModel() {

    var viewState: Bundle = Bundle()
    private var _launchesLiveData: EventLiveData<Resource<List<Launches>>> = EventLiveData()
    var launchesLiveData = _launchesLiveData

    init {
        loadLaunches()
    }

    fun loadLaunches() {
        loadData(loadLaunchesUseCase, _launchesLiveData)
    }

}
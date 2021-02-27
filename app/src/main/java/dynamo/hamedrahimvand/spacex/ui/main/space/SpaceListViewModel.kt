package dynamo.hamedrahimvand.spacex.ui.main.space

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamo.hamedrahimvand.spacex.common.base.BaseViewModel
import dynamo.hamedrahimvand.spacex.common.livedata.EventLiveData
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import dynamo.hamedrahimvand.spacex.data.model.Launch
import dynamo.hamedrahimvand.spacex.data.repository.local.db.AppPreferences
import dynamo.hamedrahimvand.spacex.data.usecase.LoadLaunchesUseCase
import javax.inject.Inject

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
@HiltViewModel
class SpaceListViewModel @Inject constructor(
    private val loadLaunchesUseCase: LoadLaunchesUseCase,
    private val appPreferences: AppPreferences
) :
    BaseViewModel() {

    var viewState: Bundle = Bundle()

    private var _launchLiveData: EventLiveData<Resource<List<Launch>>> = EventLiveData()
    var launchesLiveData = _launchLiveData

    init {
        loadLaunches(true)
    }

    fun loadNextPage() {
        loadLaunches(false)
    }

    fun loadLaunches(isRefresh: Boolean) {
        var isNextPage = false
        loadLaunchesUseCase.nextPage = if (isRefresh)
            1
        else {
            isNextPage = appPreferences.getHasNextPage()
            if (isNextPage)
                appPreferences.getLastPage() + 1
            else
                return
        }
        loadLaunchesUseCase.isRefresh = isRefresh
        loadLaunchesUseCase.isForceFetch = isNextPage
        loadData(loadLaunchesUseCase, _launchLiveData)
    }

}
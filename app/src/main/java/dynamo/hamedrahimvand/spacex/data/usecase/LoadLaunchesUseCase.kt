package dynamo.hamedrahimvand.spacex.data.usecase

import dynamo.hamedrahimvand.spacex.data.model.request_models.LaunchesRequestModel
import dynamo.hamedrahimvand.spacex.data.model.request_models.Options
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import dynamo.hamedrahimvand.spacex.data.model.ui_models.LaunchItem
import dynamo.hamedrahimvand.spacex.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
class LoadLaunchesUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<List<LaunchItem>>() {
    companion object {
        private const val LIMIT = 10
    }

    var isRefresh: Boolean = false
    var isForceFetch: Boolean = false
    var nextPage = 0
    private val requestModel: LaunchesRequestModel
        get() = LaunchesRequestModel(Options(LIMIT, nextPage))

    override suspend fun loadData(): Flow<Resource<List<LaunchItem>>> {
        return repository.loadLaunches(isRefresh, isForceFetch, requestModel)
            .also { isForceFetch = false }.map { resource ->
            val data = resource.data?.map { launchesEntity ->
                LaunchItem(launchesEntity.id, launchesEntity.name, launchesEntity.smallIcon)
            }
            Resource(resource.status, data, resource.error)
        }
    }

}
package dynamo.hamedrahimvand.spacex.data.usecase

import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import dynamo.hamedrahimvand.spacex.data.model.ui_models.Launches
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
    BaseUseCase<List<Launches>>() {
    var isForceFetch: Boolean = false
    override suspend fun loadData(): Flow<Resource<List<Launches>>> {
        val flow = repository.loadLaunches(isForceFetch).map { resource ->
            val data = resource.data?.map { launchesEntity ->
                Launches(launchesEntity.id, launchesEntity.name, launchesEntity.smallIcon)
            }
            Resource(resource.status, data, resource.error)
        }
        isForceFetch = false
        return flow
    }

}
package dynamo.hamedrahimvand.spacex.data.usecase

import dynamo.hamedrahimvand.spacex.data.model.local_models.Launches
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
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
    override suspend fun loadData(): Flow<Resource<List<Launches>>> =
        repository.loadLaunchesAsync().map { resource ->
            val data = resource.data?.map { launchesResponse ->
                Launches(launchesResponse.id, launchesResponse.name)
            }
            Resource(resource.status, data, resource.error)
        } //Todo load data from cloud temporarily

}
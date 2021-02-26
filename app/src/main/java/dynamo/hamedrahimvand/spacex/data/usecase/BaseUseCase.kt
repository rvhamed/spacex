package dynamo.hamedrahimvand.spacex.data.usecase

import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import kotlinx.coroutines.flow.Flow

/**
 * UseCases are data managers, you can just map and manipulate data in these classes.
 *
 * @author Hamed.Rahimvand
 * @since 2/26/21
 */
abstract class BaseUseCase<T> {
    abstract suspend fun loadData() : Flow<Resource<T>>
}
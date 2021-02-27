package dynamo.hamedrahimvand.spacex.data.repository

import dynamo.hamedrahimvand.spacex.data.model.ui_models.LaunchesTestModel
import dynamo.hamedrahimvand.spacex.data.model.models.LaunchesResponseTestModel
import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class RepositoryImplTest {

    var launchesListFromDb: LaunchesTestModel? = null
    var launchesResponseFromCloud: Resource<LaunchesResponseTestModel?> = Resource.success(null)
    var shouldFetch = false

    val networkBoundResource =
        object : NetworkBoundResource<LaunchesTestModel, LaunchesResponseTestModel>() {
            override suspend fun loadFromDb(): Flow<LaunchesTestModel?> = flow {
                assert(true)
                emit(launchesListFromDb)
            }

            override fun shouldFetch(data: LaunchesTestModel?): Boolean = shouldFetch

            override suspend fun createCall(): Flow<Resource<LaunchesResponseTestModel?>> {
                return flow {
                    emit(launchesResponseFromCloud)
                }
            }

            override suspend fun saveCallResult(item: Resource<LaunchesResponseTestModel?>) {
                item.data?.let { launchesResponse ->
                    launchesListFromDb =
                        LaunchesTestModel(launchesResponse.id, launchesResponse.name)
                }
            }

        }

    @Test
    fun `Given load data from db, When db is null and shouldFetch is FALSE, Then return null and Api shouldn't call`() =
        runBlocking {
            launchesListFromDb = null
            launchesResponseFromCloud = Resource.success(null)
            shouldFetch = false

            val set = networkBoundResource.asFlow().take(2).toList()
            set.forEachIndexed { index, resource ->
                when (index) {
                    0,1 -> {
                        assertThat(resource.status, equalTo(Resource.Status.LOADING))
                    }
                    2 -> {
                        assertThat(resource.status, `is`(equalTo(Resource.Status.SUCCESS)))
                        assertThat(resource.data, `is`(nullValue()))
                    }
                }
            }
        }


    @Test
    fun `Given load data from db, When db is null and shouldFetch is TRUE, Then API should call and return mock data`() =
        runBlocking {
            val launchesResponse = LaunchesResponseTestModel("1", "MyName")
            launchesListFromDb = null
            launchesResponseFromCloud = Resource.success(launchesResponse)
            shouldFetch = true

            val set = networkBoundResource.asFlow().take(3).toList()
            set.forEachIndexed { index, resource ->
                when (index) {
                    0, 1 -> {
                        assertThat(resource.status, equalTo(Resource.Status.LOADING))
                    }
                    2 -> {
                        assertThat(resource.status, `is`(equalTo(Resource.Status.SUCCESS)))
                        assertThat(resource.data?.id, equalTo(launchesResponse.id))
                        assertThat(resource.data?.name, equalTo(launchesResponse.name))
                        //Data should be saved in DB
                        assertThat(resource.data?.id, equalTo(launchesListFromDb?.id))
                        assertThat(resource.data?.name, equalTo(launchesListFromDb?.name))
                    }
                }
            }
        }

    @Test
    fun `Given load data from db, When db is not null and shouldFetch is TRUE, Then the first mock should return in second loading and API should call and return mock data`() =
        runBlocking {
            val launchesResponse = LaunchesResponseTestModel("1", "MyName")
            val launches = LaunchesTestModel("2", "DbName")
            launchesListFromDb = launches
            launchesResponseFromCloud = Resource.success(launchesResponse)
            shouldFetch = true

            val set = networkBoundResource.asFlow().take(3).toList()
            set.forEachIndexed { index, resource ->
                when (index) {
                    0 -> {
                        assertThat(resource.status, equalTo(Resource.Status.LOADING))
                    }
                    1 -> {
                        assertThat(resource.status, equalTo(Resource.Status.LOADING))
                        assertThat(resource.data?.id, equalTo(launches.id))
                        assertThat(resource.data?.name, equalTo(launches.name))
                    }
                    2 -> {
                        assertThat(resource.status, `is`(equalTo(Resource.Status.SUCCESS)))
                        assertThat(resource.data?.id, equalTo(launchesResponse.id))
                        assertThat(resource.data?.name, equalTo(launchesResponse.name))
                        //Data should be saved in DB
                        assertThat(resource.data?.id, equalTo(launchesListFromDb?.id))
                        assertThat(resource.data?.name, equalTo(launchesListFromDb?.name))
                    }
                }
            }
        }
}
package dynamo.hamedrahimvand.spacex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dynamo.hamedrahimvand.spacex.data.repository.Repository
import dynamo.hamedrahimvand.spacex.data.repository.RepositoryImpl
import dynamo.hamedrahimvand.spacex.data.repository.local.LocalDataSource
import dynamo.hamedrahimvand.spacex.data.repository.local.LocalDataSourceImpl
import dynamo.hamedrahimvand.spacex.data.repository.remote.Apis
import dynamo.hamedrahimvand.spacex.data.repository.remote.CloudDataSource
import dynamo.hamedrahimvand.spacex.data.repository.remote.CloudDataSourceImpl
import javax.inject.Singleton

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class DomainModule {

    @Singleton
    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository

    @Singleton
    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Singleton
    @Binds
    abstract fun bindCloudDataSource(cloudDataSourceImpl: CloudDataSourceImpl): CloudDataSource

    @Singleton
    @Binds
    abstract fun bindApis(): Apis
}
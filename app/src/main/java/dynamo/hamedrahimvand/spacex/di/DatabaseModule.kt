package dynamo.hamedrahimvand.spacex.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dynamo.hamedrahimvand.spacex.data.repository.local.db.AppDatabase
import dynamo.hamedrahimvand.spacex.data.repository.local.db.LaunchesDao
import javax.inject.Singleton

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/26/21
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideVenueDao(db: AppDatabase): LaunchesDao = db.launchesDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

}
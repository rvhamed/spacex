package dynamo.hamedrahimvand.spacex.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/25/21
 */
@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule{

    @Provides
    @Singleton
    fun provideSharePreference(@ApplicationContext context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

}
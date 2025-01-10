package m.a.poem.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import m.a.poem.api.storage.PrefStorage
import m.a.poem.domain.storage.LocalStorage
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Singleton
    @Provides
    fun providePrefStorage(@ApplicationContext appContext: Context): LocalStorage {
        return PrefStorage("nobahar", appContext)
    }
}
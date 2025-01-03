package m.a.poem.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.a.poem.api.contract.CenturyApi
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun providesCenturyApi(
        retrofit: Retrofit
    ): CenturyApi = retrofit.create(CenturyApi::class.java)


}
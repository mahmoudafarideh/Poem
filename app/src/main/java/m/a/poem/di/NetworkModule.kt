package m.a.poem.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.a.poem.api.contract.CenturyApi
import m.a.poem.api.contract.PoetApi
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun providesCenturyApi(retrofit: Retrofit): CenturyApi = retrofit.create(CenturyApi::class.java)

    @Provides
    fun providesPoetApi(retrofit: Retrofit): PoetApi = retrofit.create(PoetApi::class.java)


}
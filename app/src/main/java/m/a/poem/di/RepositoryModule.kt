package m.a.poem.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.a.poem.api.repository.CenturyPoetsRepositoryImp
import m.a.poem.domain.repository.CenturyPoetsRepository


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun providesConverterFactory(
        centuryPoetsRepository: CenturyPoetsRepositoryImp
    ): CenturyPoetsRepository

}
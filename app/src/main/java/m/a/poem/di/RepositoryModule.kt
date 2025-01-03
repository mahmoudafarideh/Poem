package m.a.poem.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.a.poem.api.repository.CenturyPoetsRepositoryImp
import m.a.poem.api.repository.PoetRepositoryImp
import m.a.poem.domain.repository.CenturyPoetsRepository
import m.a.poem.domain.repository.PoetRepository


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun providesCenturyPoetsRepository(
        centuryPoetsRepository: CenturyPoetsRepositoryImp
    ): CenturyPoetsRepository

    @Binds
    fun providesPoetRepository(repo: PoetRepositoryImp): PoetRepository

}
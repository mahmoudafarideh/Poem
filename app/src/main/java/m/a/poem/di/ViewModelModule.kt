package m.a.poem.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import m.a.poem.ui.book.screen.BookViewModel
import m.a.poem.ui.poet.screen.PoetViewModel


@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun poetViewModelFactory(): PoetViewModel.Factory
    fun bookViewModelFactory(): BookViewModel.Factory

}
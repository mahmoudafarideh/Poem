package m.a.poem.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import m.a.poem.ui.book.screen.BookViewModel
import m.a.poem.ui.poem.screen.PoemViewModel
import m.a.poem.ui.poet.screen.PoetViewModel
import m.a.poem.ui.search.screen.SearchViewModel
import m.a.poem.ui.widget.viewmodel.PoemWidgetViewModel


@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun poetViewModelFactory(): PoetViewModel.Factory
    fun bookViewModelFactory(): BookViewModel.Factory
    fun poemViewModelFactory(): PoemViewModel.Factory
    fun searchViewModelFactory(): SearchViewModel.Factory

}
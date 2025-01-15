package m.a.nobahar.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import m.a.nobahar.domain.model.Failed
import m.a.nobahar.domain.model.InitialFailed
import m.a.nobahar.domain.model.InitialLoading
import m.a.nobahar.domain.model.LoadableData
import m.a.nobahar.domain.model.Loaded
import m.a.nobahar.domain.model.Loading
import m.a.nobahar.domain.model.LoadingMore
import m.a.nobahar.domain.model.LoadingMoreFailed
import m.a.nobahar.domain.model.NotInitialLoaded
import m.a.nobahar.domain.model.PageLoaded
import m.a.nobahar.domain.model.PaginateLoadableData

abstract class BaseViewModel<T>(initialState: T) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected fun updateState(stateUpdate: T.() -> T) {
        _state.update(stateUpdate)
    }

    protected fun <T> executeLoadable(
        currentValue: LoadableData<T>,
        action: suspend () -> T,
        data: (LoadableData<T>) -> Unit
    ) {
        if (currentValue is Loading) return
        data(Loading)
        viewModelScope.launch {
            runCatching {
                action()
            }.onFailure {
                it.printStackTrace()
                data(Failed)
            }.onSuccess {
                data(Loaded(it))
            }
        }
    }

    protected fun <T> CoroutineScope.executePaginateLoadable(
        currentValue: PaginateLoadableData<T>,
        action: suspend (page: Int, limit: Int) -> List<T>,
        data: (PaginateLoadableData<T>) -> Unit
    ) {
        if (!currentValue.hasMorePages) return
        if (currentValue is InitialLoading || currentValue is LoadingMore) return
        if (currentValue is NotInitialLoaded || currentValue is InitialFailed) {
            data(InitialLoading(currentValue.page, currentValue.limit))
        }
        if (currentValue is PageLoaded<T> || currentValue is LoadingMoreFailed) {
            data(LoadingMore(data = currentValue.data!!, currentValue.page, currentValue.limit))
        }
        launch {
            runCatching {
                action(currentValue.page, currentValue.limit)
            }.onFailure {
                if (
                    currentValue is InitialLoading ||
                    currentValue is NotInitialLoaded ||
                    currentValue is InitialFailed
                ) {
                    data(InitialFailed(currentValue.page, currentValue.limit))
                } else {
                    data(
                        LoadingMoreFailed(
                            currentValue.data!!,
                            currentValue.page,
                            currentValue.limit
                        )
                    )
                }
            }.onSuccess {
                data(
                    PageLoaded(
                        currentValue.data.orEmpty().plus(it).toImmutableList(),
                        currentValue.page.plus(1),
                        currentValue.limit,
                        it.size >= currentValue.limit
                    )
                )
            }
        }
    }
}
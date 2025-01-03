package m.a.poem.ui.shared

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import m.a.poem.domain.model.Failed
import m.a.poem.domain.model.LoadableData
import m.a.poem.domain.model.Loaded
import m.a.poem.domain.model.Loading

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
        if(currentValue is Loading) return
        data(Loading)
        viewModelScope.launch {
            runCatching {
                action()
            }.onFailure {
                Log.d("SXO", "executeLoadable Failed ${it.message}")
                data(Failed)
            }.onSuccess {
                Log.d("SXO", "executeLoadable Succeed $it")
                data(Loaded(it))
            }
        }
    }
}
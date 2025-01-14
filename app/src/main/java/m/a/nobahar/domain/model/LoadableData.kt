package m.a.nobahar.domain.model

import androidx.compose.runtime.Stable

@Stable
sealed class LoadableData<out T> {
    abstract val data: T?
}

@Stable
data object NotLoaded: LoadableData<Nothing>() {
    override val data: Nothing? = null
}

@Stable
data object Loading: LoadableData<Nothing>() {
    override val data: Nothing? = null
}

@Stable
data class Loaded<out T>(
    override val data: T
): LoadableData<T>()

@Stable
data object Failed: LoadableData<Nothing>() {
    override val data: Nothing? = null
}


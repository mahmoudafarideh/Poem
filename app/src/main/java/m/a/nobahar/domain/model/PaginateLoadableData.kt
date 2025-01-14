package m.a.nobahar.domain.model

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

@Stable
sealed class PaginateLoadableData<out T> {
    abstract val data: ImmutableList<T>?
    abstract val page: Int
    abstract val limit: Int
    abstract val hasMorePages: Boolean
}

@Stable
data class NotInitialLoaded(
    override val page: Int,
    override val limit: Int,
) : PaginateLoadableData<Nothing>() {
    override val data: Nothing? = null
    override val hasMorePages: Boolean = true
}

@Stable
data class InitialLoading(
    override val page: Int,
    override val limit: Int,
) : PaginateLoadableData<Nothing>() {
    override val data: Nothing? = null
    override val hasMorePages: Boolean = true
}

@Stable
data class LoadingMore<out T>(
    override val data: ImmutableList<T>,
    override val page: Int,
    override val limit: Int,
) : PaginateLoadableData<T>() {
    override val hasMorePages: Boolean = true
}

@Stable
data class PageLoaded<out T>(
    override val data: ImmutableList<T>,
    override val page: Int,
    override val limit: Int,
    override val hasMorePages: Boolean = true
) : PaginateLoadableData<T>()

@Stable
data class InitialFailed(
    override val page: Int,
    override val limit: Int,
) : PaginateLoadableData<Nothing>() {
    override val data: Nothing? = null
    override val hasMorePages: Boolean = true
}

@Stable
data class LoadingMoreFailed<out T>(
    override val data: ImmutableList<T>,
    override val page: Int,
    override val limit: Int,
) : PaginateLoadableData<T>() {
    override val hasMorePages: Boolean = true
}


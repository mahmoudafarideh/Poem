package m.a.nobahar.domain.storage

interface LocalStorage {
    suspend fun <T : Any> getData(key: String, clazz: Class<T>, defaultValue: T?): T?
    suspend fun <T : Any> setData(key: String, clazz: Class<T>, value: T?)
}

inline fun <reified T : Any> LocalStorage.data(key: String, defaultValue: T) =
    LocalStorageDelegate(this, key, T::class.java, defaultValue)

inline fun <reified T : Any> LocalStorage.optional(key: String, defaultValue: T? = null) =
    LocalStorageDelegate(this, key, T::class.java, defaultValue)

class LocalStorageDelegate<T : Any>(
    private val localStorage: LocalStorage,
    private val key: String,
    private val clazz: Class<T>,
    private val defaultValue: T?
) {
    suspend fun getValue() = localStorage.getData(key, clazz, defaultValue)
    suspend fun updateValue(newValue: T) = localStorage.setData(key, clazz, newValue)
}
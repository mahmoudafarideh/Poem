package m.a.poem.domain.storage

interface LocalStorage {
    fun <T : Any> getData(key: String, clazz: Class<T>, defaultValue: T?): T?
    fun <T : Any> setData(key: String, clazz: Class<T>, value: T?)
}
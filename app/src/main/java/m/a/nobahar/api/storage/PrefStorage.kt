package m.a.nobahar.api.storage

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import m.a.nobahar.domain.storage.LocalStorage

@Suppress("UNCHECKED_CAST")
class PrefStorage(
    private val name: String,
    context: Context
) : LocalStorage {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(name, Activity.MODE_PRIVATE)
    }

    override suspend fun <T : Any> getData(key: String, clazz: Class<T>, defaultValue: T?): T? {
        return withContext(Dispatchers.IO) {
            getData(key, defaultValue, clazz)
        }
    }

    private fun <T : Any> getData(key: String, defaultValue: T?, clazz: Class<T>): T? {
        if (!sharedPreferences.contains(key)) return defaultValue
        return when (clazz) {
            Int::class.java, Integer::class.java ->
                sharedPreferences.getInt(key, (defaultValue ?: 0) as Int) as T

            Long::class.java, java.lang.Long::class.java ->
                sharedPreferences.getLong(key, (defaultValue ?: 0L) as Long) as T

            String::class.java ->
                sharedPreferences.getString(key, defaultValue as String?) as T?

            Boolean::class.java, java.lang.Boolean::class.java ->
                sharedPreferences.getBoolean(key, (defaultValue ?: false) as Boolean) as T

            else -> throw IllegalArgumentException()
        }
    }

    override suspend fun <T : Any> setData(key: String, clazz: Class<T>, value: T?) {
        withContext(Dispatchers.IO) {
            updateData(value, key, clazz)
        }
    }

    private fun <T : Any> updateData(value: T?, key: String, clazz: Class<T>) {
        val editor = sharedPreferences.edit()
        if (value == null) {
            editor.remove(key)
        } else {
            when (clazz) {
                Int::class.java, Integer::class.java ->
                    editor.putInt(key, value as Int)

                String::class.java, java.lang.String::class.java ->
                    editor.putString(key, value as String?)

                Long::class.java, java.lang.Long::class.java ->
                    editor.putLong(key, value as Long) as T

                Boolean::class.java, java.lang.Boolean::class.java ->
                    editor.putBoolean(key, value as Boolean)

                else -> throw IllegalArgumentException()
            }
        }
        editor.apply()
    }
}
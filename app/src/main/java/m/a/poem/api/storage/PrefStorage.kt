package m.a.poem.api.storage

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import m.a.poem.domain.storage.LocalStorage
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class PrefStorage(
    private val name: String,
    context: Context
) : LocalStorage {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(name, Activity.MODE_PRIVATE)
    }

    override fun <T : Any> getData(key: String, clazz: Class<T>, defaultValue: T?): T? {
        if (!sharedPreferences.contains(key)) return defaultValue
        return when (clazz) {
            Int::class.java, Integer::class.java ->
                sharedPreferences.getInt(key, (defaultValue) as Int) as T

            Long::class.java, java.lang.Long::class.java ->
                sharedPreferences.getLong(key, (defaultValue ?: 0) as Long) as T

            String::class.java ->
                sharedPreferences.getString(key, defaultValue as String?) as T?

            Boolean::class.java, java.lang.Boolean::class.java ->
                sharedPreferences.getBoolean(key, (defaultValue ?: false) as Boolean) as T

            else -> throw IllegalArgumentException()
        }
    }

    override fun <T : Any> setData(key: String, clazz: Class<T>, value: T?) {
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

inline fun <reified T : Any> LocalStorage.optional(
    key: String,
    default: T? = null
): ReadWriteProperty<Any?, T?> {

    return object : ReadWriteProperty<Any?, T?> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T? =
            getData(key, T::class.java, default)

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            setData(key, T::class.java, value)
        }
    }
}

inline fun <reified T : Any> LocalStorage.data(
    key: String, default: T
): ReadWriteProperty<Any?, T> {

    return object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T =
            getData(key, T::class.java, default)!!

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            setData(key, T::class.java, value)
        }
    }
}

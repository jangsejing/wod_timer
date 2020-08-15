package com.jess.wodtimer.common.manager

import android.content.Context
import androidx.preference.PreferenceManager
import com.jess.wodtimer.common.util.tryCatch
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * @author jess
 * @since 2020.08.11
 */
interface PreferencesManager {

    fun put(
        key: String,
        value: Any?
    )

    fun remove(key: String)

    fun clear()

    fun getString(
        key: String,
        defaultValue: String? = null
    ): String?

    fun getBoolean(
        key: String,
        defaultValue: Boolean = false
    ): Boolean

    fun getLong(
        key: String,
        defaultValue: Long = 0L
    ): Long

    fun getDouble(
        key: String,
        defaultValue: Double
    ): Double

    fun getInt(
        key: String,
        defaultValue: Int = 0
    ): Int

    fun getFloat(
        key: String,
        defaultValue: Float = 0F
    ): Float
}

class PreferencesManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesManager {

    private val preferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    /**
     * 값 저장
     */
    override fun put(
        key: String,
        value: Any?
    ) {
        tryCatch {
            val edit = preferences.edit()
            when (value) {
                is Double -> edit?.putLong(key, java.lang.Double.doubleToRawLongBits(value))
                is Int -> edit?.putInt(key, value)
                is Long -> edit?.putLong(key, value)
                is Float -> edit?.putFloat(key, value)
                is Boolean -> edit?.putBoolean(key, value)
                else -> edit?.putString(key, value?.toString())
            }
            edit?.apply()
        }
    }

    override fun remove(key: String) {
        val edit = preferences.edit()
        edit?.remove(key)
        edit?.apply()
    }

    override fun clear() {
        val edit = preferences.edit()
        preferences.all.forEach {
            edit?.remove(it.key)
        }
        edit?.apply()
    }

    override fun getString(
        key: String,
        defaultValue: String?
    ): String? {
        val result = preferences.getString(key, defaultValue)
        Timber.d("[String] $key : $result")
        return result
    }

    override fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean {
        val result = preferences.getBoolean(key, defaultValue)
        Timber.d("[Boolean] $key : $result")
        return result
    }

    override fun getLong(
        key: String,
        defaultValue: Long
    ): Long {
        val result = preferences.getLong(key, defaultValue)
        Timber.d("[Long] $key : $result")
        return result
    }

    override fun getDouble(
        key: String,
        defaultValue: Double
    ): Double {
        val result = java.lang.Double.longBitsToDouble(
            preferences.getLong(
                key,
                java.lang.Double.doubleToLongBits(defaultValue)
            )
        )
        Timber.d("[Double] $key : $result")
        return result
    }

    override fun getInt(
        key: String,
        defaultValue: Int
    ): Int {
        val result = preferences.getInt(key, defaultValue)
        Timber.d("[Int] $key : $result")
        return result

    }

    override fun getFloat(
        key: String,
        defaultValue: Float
    ): Float {
        val result = preferences.getFloat(key, defaultValue)
        Timber.d("[Float] $key : $result")
        return result
    }
}
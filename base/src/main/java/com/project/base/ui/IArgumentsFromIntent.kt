package com.project.base.ui

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import java.util.*

interface IArgumentsFromIntent {

    /**
     * 传递 Intent，可以在这里获取
     */
    fun getIntent(): Intent?

    fun getBooleanArrayFromIntent(key: String?): BooleanArray? {
        return getIntent()?.getBooleanArrayExtra(key)
    }

    fun getBooleanFromIntent(
        key: String?,
        defValue: Boolean = false
    ): Boolean {
        return getIntent()?.getBooleanExtra(key, defValue) ?: false
    }

    fun getByteArrayFromIntent(key: String?): ByteArray? {
        return getIntent()?.getByteArrayExtra(key)
    }

    fun getByteFromIntent(key: String?, defValue: Byte = 0): Byte {
        return getIntent()?.getByteExtra(key, defValue) ?: 0
    }

    fun getCharArrayFromIntent(key: String?): CharArray? {
        return getIntent()?.getCharArrayExtra(key)
    }

    fun getCharFromIntent(key: String?, defValue: Char = ' '): Char {
        return getIntent()?.getCharExtra(key, defValue) ?: ' '
    }

    fun getCharSequenceArrayFromIntent(key: String?): Array<CharSequence?>? {
        return getIntent()?.getCharSequenceArrayExtra(key)
    }

    fun getCharSequenceArrayListFromIntent(key: String?): ArrayList<CharSequence?>? {
        return getIntent()?.getCharSequenceArrayListExtra(key)
    }

    fun getCharSequenceFromIntent(key: String?): CharSequence? {
        return getIntent()?.getCharSequenceExtra(key)
    }

    fun getDoubleArrayFromIntent(key: String?): DoubleArray? {
        return getIntent()?.getDoubleArrayExtra(key)
    }

    fun getDoubleFromIntent(key: String?, defValue: Double = 0.0): Double {
        return getIntent()?.getDoubleExtra(key, defValue) ?: 0.0
    }

    fun getExtrasFromIntent(key: String?): Bundle? {
        return getIntent()?.extras
    }

    fun getFloatArrayFromIntent(key: String?): FloatArray? {
        return getIntent()?.getFloatArrayExtra(key)
    }

    fun getFloatFromIntent(key: String?, defValue: Float = 0f): Float {
        return getIntent()?.getFloatExtra(key, defValue) ?: 0f
    }

    fun getIntArrayFromIntent(key: String?): IntArray? {
        return getIntent()?.getIntArrayExtra(key)
    }

    fun getIntegerArrayListFromIntent(key: String?): ArrayList<Int?>? {
        return getIntent()?.getIntegerArrayListExtra(key)
    }

    fun getIntFromIntent(key: String?, defValue: Int = 0): Int {
        return getIntent()?.getIntExtra(key, defValue) ?: 0
    }

    fun getLongArrayFromIntent(key: String?): LongArray? {
        return getIntent()?.getLongArrayExtra(key)
    }

    fun getLongFromIntent(key: String?, defValue: Long = 0): Long {
        return getIntent()?.getLongExtra(key, defValue) ?: 0
    }

    /**
     * 如果传递的是多种类型的 Parcelable 数组，使用这个方法
     */
    fun getParcelableArrayFromIntent(key: String?): Array<Parcelable?>? {
        return getIntent()?.getParcelableArrayExtra(key)
    }

    /**
     * 如果传递是一种类型的 Parcelable 数组，使用这个方法
     */
    fun <T: Parcelable> getParcelableArrayFromIntent2(key: String?): List<T?>? {
        val arr = getIntent()?.getParcelableArrayExtra(key)
        val list = mutableListOf<T>()
        arr?.let {
            it.forEach { parcelable ->
                @Suppress("UNCHECKED_CAST")
                list.add(parcelable as T)
            }
        }
        return list
    }

    /**
     * 传递一种类型的 Parcelable 列表，使用这个方法
     */
    fun <T : Parcelable> getParcelableArrayListFromIntent(key: String?): ArrayList<T?>? {
        return getIntent()?.getParcelableArrayListExtra(key)
    }

    fun <T : Parcelable> getParcelableFromIntent(key: String?): T? {
        return getIntent()?.getParcelableExtra(key)
    }

    fun getSerializableFromIntent(key: String?): Serializable? {
        return getIntent()?.getSerializableExtra(key)
    }

    fun getShortArrayFromIntent(key: String?): ShortArray? {
        return getIntent()?.getShortArrayExtra(key)
    }

    fun getShortFromIntent(key: String?, defValue: Short = 0): Short {
        return getIntent()?.getShortExtra(key, defValue) ?: 0
    }

    fun getStringArrayFromIntent(key: String?): Array<String?>? {
        return getIntent()?.getStringArrayExtra(key)
    }

    fun getStringArrayListFromIntent(key: String?): ArrayList<String?>? {
        return getIntent()?.getStringArrayListExtra(key)
    }

    fun getStringFromIntent(key: String?): String? {
        return getIntent()?.getStringExtra(key)
    }
}
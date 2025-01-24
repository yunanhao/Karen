package com.project.base.ui

import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import java.io.Serializable
import java.util.*

interface IArgumentsFromBundle {
    /**
     * 传递 bundle，在这里可以获取
     */
    fun getArguments(): Bundle?

    fun getByteFromBundle(key: String?, defValue: Byte = 0): Byte {
        return getArguments()?.getByte(key, defValue) ?: 0
    }

    fun getCharFromBundle(key: String?, defValue: Char = ' '): Char {
        return getArguments()?.getChar(key, defValue) ?: ' '
    }

    fun getShortFromBundle(key: String?, defValue: Short = 0): Short {
        return getArguments()?.getShort(key, defValue) ?: 0
    }

    fun getFloatFromBundle(key: String?, defValue: Float = 0f): Float {
        return getArguments()?.getFloat(key, defValue) ?: 0f
    }

    fun getCharSequenceFromBundle(
        key: String?,
        defValue: CharSequence? = null
    ): CharSequence? {
        return getArguments()?.getCharSequence(key, defValue)
    }

    fun getSizeFromBundle(key: String?): Size? {
        return getArguments()?.getSize(key)
    }

    fun getSizeFFromBundle(key: String?): SizeF? {
        return getArguments()?.getSizeF(key)
    }

    fun getBundleFromBundle(key: String?): Bundle? {
        return getArguments()?.getBundle(key)
    }

    fun <T : Parcelable> getParcelableFromBundle(key: String?): T? {
        return getArguments()?.getParcelable(key)
    }

    fun getParcelableArrayFromBundle(key: String?): Array<Parcelable?>? {
        return getArguments()?.getParcelableArray(key)
    }

    fun <T : Parcelable> getParcelableArrayListFromBundle(key: String?): ArrayList<T>? {
        return getArguments()?.getParcelableArrayList(key)
    }

    fun <T : Parcelable?> getSparseParcelableArrayFromBundle(key: String?): SparseArray<T>? {
        return getArguments()?.getSparseParcelableArray(key)
    }

    fun getSerializableFromBundle(key: String?): Serializable? {
        return getArguments()?.getSerializable(key)
    }

    fun getIntegerArrayListFromBundle(key: String?): ArrayList<Int?>? {
        return getArguments()?.getIntegerArrayList(key)
    }

    fun getStringArrayListFromBundle(key: String?): ArrayList<String?>? {
        return getArguments()?.getStringArrayList(key)
    }

    fun getCharSequenceArrayListFromBundle(key: String?): ArrayList<CharSequence?>? {
        return getArguments()?.getCharSequenceArrayList(key)
    }

    fun getByteArrayFromBundle(key: String?): ByteArray? {
        return getArguments()?.getByteArray(key)
    }

    fun getShortArrayFromBundle(key: String?): ShortArray? {
        return getArguments()?.getShortArray(key)
    }

    fun getCharArrayFromBundle(key: String?): CharArray? {
        return getArguments()?.getCharArray(key)
    }

    fun getFloatArrayFromBundle(key: String?): FloatArray? {
        return getArguments()?.getFloatArray(key)
    }

    fun getCharSequenceArrayFromBundle(key: String?): Array<CharSequence?>? {
        return getArguments()?.getCharSequenceArray(key)
    }

    fun getBinderFromBundle(key: String?): IBinder? {
        return getArguments()?.getBinder(key)
    }

    fun getBooleanFromBundle(key: String?, defValue: Boolean = false): Boolean? {
        return getArguments()?.getBoolean(key, defValue)
    }

    fun getIntFromBundle(key: String?, defValue: Int = 0): Int {
        return getArguments()?.getInt(key, defValue) ?: 0
    }

    fun getLongFromBundle(key: String?, defValue: Long = 0): Long {
        return getArguments()?.getLong(key, defValue) ?: 0
    }

    fun getDoubleFromBundle(key: String?, defValue: Double = 0.0): Double {
        return getArguments()?.getDouble(key, defValue) ?: 0.0
    }

    fun getStringFromBundle(key: String?, defValue: String? = null): String? {
        return getArguments()?.getString(key, defValue)
    }

    fun getBooleanArrayFromBundle(key: String?): BooleanArray? {
        return getArguments()?.getBooleanArray(key)
    }

    fun getIntArrayFromBundle(key: String?): IntArray? {
        return getArguments()?.getIntArray(key)
    }

    fun getLongArrayFromBundle(key: String?): LongArray? {
        return getArguments()?.getLongArray(key)
    }

    fun getDoubleArrayFromBundle(key: String?): DoubleArray? {
        return getArguments()?.getDoubleArray(key)
    }

    fun getStringArrayFromBundle(key: String?): Array<String?>? {
        return getArguments()?.getStringArray(key)
    }
}
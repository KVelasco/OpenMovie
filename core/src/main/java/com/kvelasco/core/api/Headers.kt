package com.kvelasco.core.api

import com.kvelasco.core.api.Headers.Builder
import java.util.TreeSet

class Headers private constructor(namesAndValues: Array<String?>) {

    private val namesAndValues: Array<String?>

    constructor(builder: Builder) : this(builder.namesAndValues.toTypedArray())

    init {
        this.namesAndValues = namesAndValues
    }

    fun names(): Set<String> {
        val result = TreeSet<String>(String.CASE_INSENSITIVE_ORDER)

        val size = size()
        for (i in 0 until size) {
            result.add(name(i))
        }
        return HashSet<String>(result)
    }

    fun name(index: Int): String {
        return namesAndValues[index * 2]!!
    }

    fun values(name: String): List<String?> {
        val result = mutableListOf<String?>()

        val size = size()
        for (i in 0 until size) {
            if (name.equals(name(i), true)) {
                result.add(value(i))
            }
        }
        return result
    }

    fun value(index: Int): String? {
        return namesAndValues[index * 2 + 1]
    }

    fun size(): Int {
        return namesAndValues.size / 2
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()

        val size = size()
        for (i in 0 until size) {
            stringBuilder.appendln("${name(i)}: ${value(i)}")
        }

        return stringBuilder.toString().trim()
    }

    operator fun get(name: String): String? {
        var i = namesAndValues.size - 2
        while (i >= 0) {
            if (name.equals(namesAndValues[i], true)) {
                return namesAndValues[i + 1]
            }
            i -= 2
        }
        return null
    }

    companion object {

        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }

        @JvmStatic
        fun of(headers: Map<String, String>): Headers {
            // Make a defensive copy and clean it up.
            val namesAndValues = arrayOfNulls<String>(headers.size * 2)
            var i = 0
            for ((key, value1) in headers) {
                val name = key.trim()
                val value = value1.trim()
                if (name.length == 0 ||
                    name.indexOf('\u0000') != -1 ||
                    value.indexOf('\u0000') != -1) {
                    throw IllegalArgumentException("Unexpected header: $name: $value")
                }
                namesAndValues[i] = name
                namesAndValues[i + 1] = value
                i += 2
            }

            return Headers(namesAndValues)
        }
    }

    class Builder internal constructor() {
        internal val namesAndValues = arrayListOf<String?>()

        fun add(line: String): Builder {
            val index = line.indexOf(":")
            if (index == -1) {
                throw IllegalArgumentException("Unexpected header: $line")
            }
            return add(line.substring(0, index).trim(), line.substring(index + 1))
        }

        fun add(name: String, value: String): Builder {
            checkNameAndValue(name, value)
            return addLenient(name, value)
        }

        fun addLenient(name: String, value: String): Builder {
            namesAndValues.add(name)
            namesAndValues.add(value.trim())
            return this
        }

        fun build(): Headers {
            return Headers(this)
        }

        private fun checkNameAndValue(name: String, value: String) {
            if (name.isEmpty()) {
                throw IllegalArgumentException("name is empty")
            }
            var i = 0
            var length = name.length
            while (i < length) {
                val c = name[i]
                if (c <= '\u0020' || c >= '\u007f') {
                    throw IllegalArgumentException(String.format(
                        "Unexpected char %#04x at %d in header name: %s", c.toInt(), i, name))
                }
                i++
            }

            i = 0
            length = value.length
            while (i < length) {
                val c = value[i]
                if (c <= '\u001f' && c != '\t' || c >= '\u007f') {
                    throw IllegalArgumentException(String.format(
                        "Unexpected char %#04x at %d in %s value: %s", c.toInt(), i, name, value))
                }
                i++
            }
        }
    }
}
package com.github.jing332.frpandroid.util

object StringUtils {
    fun String.removeAnsiCodes(): String {
        val ansiRegex = Regex("\\x1B\\[[0-9;]*[m|K]")
        return this.replace(ansiRegex, "")
    }

    private fun paramsParseInternal(params: String): HashMap<String, String> {
        val parameters: HashMap<String, String> = hashMapOf()
        if (params.isBlank()) return parameters

        for (param in params.split("&")) {
            val entry = param.split("=".toRegex()).dropLastWhile { it.isEmpty() }
            if (entry.size > 1) {
                parameters[entry[0]] = entry[1]
            } else {
                parameters[entry[0]] = ""
            }
        }
        return parameters
    }

    fun String.paramsParse() = paramsParseInternal(this)

    fun String.toNumberInt(): Int {
        return this.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
    }
}
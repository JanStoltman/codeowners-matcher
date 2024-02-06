import java.io.File

object Matcher {
    private val parts = mapOf(
        "*" to "[^/]+",
        "?" to "[^/]",
        "**" to ".*",
        "/**" to "/.*",
        "**/" to ".*\\/",
        "/**/" to "\\/([^\\/]+\\/)*"
    )

    fun match(codeownersFile: File, matchee: String): List<String> = match(codeownersFile.readLines(), matchee)

    fun match(lines: List<String>, matchee: String): List<String> {
        return lines.filter { it.trim().startsWith("#").not() } // Skip comments
            .reversed() // Last owner match takes precedence
            .first { isMatch(it.split(" ")[0], matchee) }
            .let { it.removePrefix(it.split(" ")[0]).trim().split(" ") }
    }

    private fun isMatch(ownerPattern: String, filePath: String): Boolean {
        var regex = "/(/?\\*\\*/?)|(\\*)|\\?|(\\[.+?])/i".toRegex().replace(ownerPattern) {
            parts[it.value] ?: it.value
        }

        regex = if (regex.startsWith("/")) {
            "^" + regex.substring(1)
        } else {
            "^(.+\\/)?$regex"
        }

        regex = if (regex.endsWith("/")) {
            regex.substring(0, regex.length - 1) + "\\/.+$"
        } else if (regex.contains("*") && !regex.contains("**")) {
            "$regex$"
        } else {
            "$regex(\\/.+)?$"
        }

        return regex.toRegex()
            .containsMatchIn(filePath)
    }
}
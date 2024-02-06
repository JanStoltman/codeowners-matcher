import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MatcherTest {

    @Test
    fun `reversed order of matches`() {
        val input = """
            /apps/ @octocat
            /apps/github
            /apps/github @doctocat
        """.trimIndent()

        val result = Matcher.match(input.lines(), "apps/github/README.md")
        assertEquals(listOf("@doctocat"), result)
    }

    @Test
    fun `match with wildcard`() {
        val input = """
            /apps/ @octocat
            /apps/* @doctocat
        """.trimIndent()

        val result = Matcher.match(input.lines(), "apps/github/README.md")
        assertEquals(listOf("@doctocat"), result)
    }

    @Test
    fun `match with double wildcard`() {
        val input = """
            /apps/ @octocat
            /apps/** @doctocat
        """.trimIndent()

        val result = Matcher.match(input.lines(), "apps/github/README.md")
        assertEquals(listOf("@doctocat"), result)
    }

    @Test
    fun `match with double wildcard and file`() {
        val input = """
            /apps/ @octocat
            /apps/**/README.md @doctocat
        """.trimIndent()

        val result = Matcher.match(input.lines(), "apps/github/README.md")
        assertEquals(listOf("@doctocat"), result)
    }

    @Test
    fun `match with double wildcard and file in subfolder`() {
        val input = """
            /apps/ @octocat
            /apps/**/README.md @doctocat
        """.trimIndent()

        val result = Matcher.match(input.lines(), "apps/github/docs/README.md")
        assertEquals(listOf("@doctocat"), result)
    }

    @Test
    fun `match with double wildcard and file in subfolder with owner`() {
        val input = """
            /apps/ @octocat
            /apps/**/README.md @doctocat
            /apps/**/docs/README.md @doctocat
        """.trimIndent()

        val result = Matcher.match(input.lines(), "apps/github/docs/README.md")
        assertEquals(listOf("@doctocat"), result)
    }

    @Test
    fun `match with double wildcard and file in subfolder with owner in reverse order`() {
        val input = """
            /apps/ @octocat
            /apps/**/README.md @doctocat
            /apps/**/docs/README.md @doctocat
        """.trimIndent()

        val result = Matcher.match(input.lines(), "apps/github/docs/README.md")
        assertEquals(listOf("@doctocat"), result)
    }
}
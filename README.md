# CODEOWNERS matcher
This repository contains a simple kotlin CODEOWNERS matcher that can be used to match a file path to a list of GitHub [CODEOWNERS](https://docs.github.com/en/repositories/managing-your-repositorys-settings-and-features/customizing-your-repository/about-code-owners#codeowners-syntax).
Given a file path, and the CODEOWNERS file it'll return a list of owners matching the file path. 

## Usage
Just copy the matcher and use it in your project. If there's interest in this library, let me know and I'll publish it as an artifact.

Given a CODEOWNERS file like:
```codeowners
/apps/ @octocat
/apps/**/README.md @doctocat
/apps/**/docs/README.md @doctocat
```

And a file path like `/apps/foo/README.md`.

A call to the Matcher.match function will return `@doctocat`.

```kotlin
val codeowners = """
    /apps/ @octocat
    /apps/**/README.md @doctocat
    /apps/**/docs/README.md @doctocat
""".trimIndent().lines()

val path = "/apps/foo/README.md"

Matcher.match(codeowners, path) // Returns @doctocat
``` 
```

## See also
- [timoschinkel/codeowners](https://github.com/timoschinkel/codeowners) - A PHP implementation of the CODEOWNERS matcher that inspired this repository.
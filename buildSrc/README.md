便于[共享子项目之间的构建逻辑](https://docs.gradle.org/current/samples/sample_convention_plugins.html)。

# DSL
本工程使用kotlin构建了三种脚本（DSL），或者说三个插件，**用以定义项目构建时候的约束条件**：
- [xyz.puppylpg.java-common-conventions.gradle.kts](src/main/kotlin/xyz.puppylpg.java-common-conventions.gradle.kts)：公共构建逻辑；
- [xyz.puppylpg.java-application-conventions.gradle.kts](src/main/kotlin/xyz.puppylpg.java-application-conventions.gradle.kts)：app的构建逻辑，依赖公共构建逻辑；
- [xyz.puppylpg.java-library-conventions.gradle.kts](src/main/kotlin/xyz.puppylpg.java-library-conventions.gradle.kts)：库的构建逻辑，依赖公共构建逻辑；

## java-common-conventions
定义了所需要的：
- dependency:
    - commons-text
    - jupiter
- dependency repo
    - maven central
- plugin和一些插件的详细执行逻辑。插件分为gradle的internal和external插件
  - `java`：gradle的[java plugin](https://docs.gradle.org/current/userguide/java_plugin.html)
  - `idea`：gradle的[idea plugin](https://docs.gradle.org/current/userguide/idea_plugin.html)，适配IDE
  - `checkstyle`：要编译的代码必须满足`checkstyle.xml`
  - `id("com.github.spotbugs")`：external插件。

最后还新加了`test`任务和`check`任务（强制要求**必须存在README.md**，且满足一定格式）。

## java-library-conventions
引用了common：
```kotlin
plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("xyz.puppylpg.java-common-conventions")

    // ...
}
```
除此之外，还新加入了两个插件：
- `java-library`
- `maven-publish`：用于发包，相当于maven的install和publish。

## java-application-conventions
同理，引用了common，除此之外，还新加入了：
- `application`插件

# 使用
**定义插件主要是为了使用**。三个插件其中一个是共有逻辑，另外两个是分别用于发包和不发包的两类项目的。

在其他子项目里，比如app子项目，`build.gradle`引用了上述`java-application-conventions`插件：
```kotlin
plugins {
    id("xyz.puppylpg.java-application-conventions")
}
```

# convention plugins
上述的脚本不是插件，却可以像插件一样被引用，主要都是靠`kotlin-dsl`插件（如果是gradle写的，需要用`groovy-gradle-plugin`插件），把dsl变成了[precompiled script plugins](https://docs.gradle.org/current/userguide/custom_plugins.html#sec:precompiled_plugins)。所以本项目需要在`build.gradle.kts`里引入该插件：
```kotlin
plugins {
    `kotlin-dsl`
}
```
In addition to plugins written as standalone projects, Gradle also allows you to provide build logic written in either Groovy or Kotlin DSLs as precompiled script plugins. You write these as `*.gradle` files in `src/main/groovy` directory or `*.gradle.kts` files in `src/main/kotlin` directory.

> Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.

如果DSL里用到了别的插件，需要像依赖一样被放到classpath上，也就是[加入到dependencies里](https://docs.gradle.org/current/userguide/custom_plugins.html#applying_external_plugins_in_precompiled_script_plugins)。

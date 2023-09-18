便于[共享子项目之间的构建逻辑](https://docs.gradle.org/current/samples/sample_convention_plugins.html)。

# DSL
本工程使用kotlin构建了三种脚本（DSL）：
- common：公共构建逻辑；
- application：app的构建逻辑；
- library：库的构建逻辑；

## common
定义了依赖仓库、依赖、插件和一些插件的详细执行逻辑。

比如**checkstyle**、idea、**readme文件check**。

## library
引用了common：
```kotlin
plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("xyz.puppylpg.java-common-conventions")

    // ...
}
```
新加入了java-library插件和maven-publish插件，后者用于发包，相当于maven的install和publish。

## app
同理，引用了common，新加入了application插件。

## 使用
最后，在其他子项目里，比如app子项目，`build.gradle`引用了上述app相关的脚本：
```kotlin
plugins {
    id("xyz.puppylpg.java-application-conventions")
}
```

# convention plugins
上述的脚本不是插件，却可以像插件一样被引用，主要都是靠`kotlin-dsl`插件（如果是gradle写的，需要用`groovy-gradle-plugin`插件），把dsl变成了[precompiled script plugins](https://docs.gradle.org/current/userguide/custom_plugins.html#sec:precompiled_plugins)：
```kotlin
plugins {
    `kotlin-dsl`
}
```
In addition to plugins written as standalone projects, Gradle also allows you to provide build logic written in either Groovy or Kotlin DSLs as precompiled script plugins. You write these as `*.gradle` files in `src/main/groovy` directory or `*.gradle.kts` files in `src/main/kotlin` directory.

> Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.

如果DSL里用到了别的插件，需要像依赖一样被放到classpath上，也就是[加入到dependencies里](https://docs.gradle.org/current/userguide/custom_plugins.html#applying_external_plugins_in_precompiled_script_plugins)。

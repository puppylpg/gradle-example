# 插件
直接引用`buildSrc`里关于app的DSL，同时定义了application插件的行为。

此外注册了几个小任务：
- welcome
- hello
- world

**现在可以使用`./gradlew :app:tasks`看到他们**。或者可以直接在idea侧边栏的gradle的`app`子项目的`other` task里找到他们。

**也可以直接使用`./gradlew :app:welcome`执行`welcome`任务**。

# 依赖
在依赖部分，把另一个子项目`utilities`当做自己的 **`implementation`依赖**（有别于`api`依赖，参考`utilities`子项目）：
```kotlin
dependencies {
    implementation("org.apache.commons:commons-text")
    implementation(project(":utilities"))
}
```

---

如果不写README.md，会导致check task报错：
> A problem was found with the configuration of task ':app:readmeCheck' (type 'ReadmeVerificationTask').
> - In plugin 'xyz.puppylpg.java-common-conventions' type 'xyz.puppylpg.ReadmeVerificationTask' property 'readme' specifies file 'xxx\gradle-example\app\README.md' which doesn't exist.

## API
readme check会检查这俩标题：
> README should contain section: ^## API$

## Changelog
> README should contain section: ^## Changelog$

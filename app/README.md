直接引用buildSrc里关于app的DSL，同时定义了application插件的行为。

在依赖部分，把另一个子项目utilities当做自己的implementation依赖：
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

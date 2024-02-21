# 插件
直接引用`buildSrc`里关于library的DSL。
```kotlin
plugins {
    id("xyz.puppylpg.java-library-conventions")
}
```

# 依赖
在依赖部分，把另一个子项目`list`当做自己的 **`api`依赖**：
```kotlin
dependencies {
    api(project(":list"))
}
```
api依赖和implementation依赖不同的地方在于：使用api依赖，**`utilities`会把`list`依赖暴露出去，变成transitive依赖**。依赖utilities的项目也可以使用list。如果这里使用`implementation`，则不会暴露list依赖。

> maven貌似没这个功能。transitive依赖一定会存在。

有什么作用呢？相当于挑明了app项目只用了utilities，用不到list。所以list项目变动的时候，只需要编译utilities就行了，不需要编译app项目
> 参考[这个回答](https://stackoverflow.com/a/44419574/7676237)。

---

如果不写README.md，会导致check task报错：

## API
README should contain section: ^## API$

## Changelog
README should contain section: ^## Changelog$

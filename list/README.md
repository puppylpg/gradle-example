# 插件
直接引用`buildSrc`里关于library的DSL。
```kotlin
plugins {
    id("xyz.puppylpg.java-library-conventions")
}
```

---

如果不写README.md，会导致check task报错：

## API
README should contain section: ^## API$

## Changelog
README should contain section: ^## Changelog$

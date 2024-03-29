# tutorial
教程参考官方[gradle tutorial](https://docs.gradle.org/current/userguide/part1_gradle_init.html)，建议使用sdkman安装gradle和配套的jdk。

本工程是使用`gradle init`创建的multiple subprojects。

使用方法：
```
./gradlew run
```

> 当然，也可以在idea里点侧边栏里相应的gradle任务，不过idea所使用的gradle不要从wrapper修改为本地的gradle。

## gradle
gradle相关的文件有很多：
- 需要排除的：
  - `.gradle/`：gradle的cache，需要exclude；
  - `build/`：子项目下编译后的产物，类似maven的target，需要exclude；
- 不需要排除的：
  - [gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html#gradle_wrapper)
    - `gradle/`：被`gradlew`脚本调用；
    - `gradlew`（`gradlew.bat`）：gradle的执行脚本，即使本地不安装gradle，也可以用来执行gradle。官方[建议加入到VCS](https://docs.gradle.org/current/userguide/part1_gradle_init.html#step_3_understanding_the_gradle_wrapper)，这样就可以[保证一致的构建过程](https://stackoverflow.com/questions/20348451/why-should-the-gradle-wrapper-be-committed-to-vcs)。
    >   These scripts allow you to run a Gradle build without requiring that Gradle be installed on your system. It also helps ensure that the same version of Gradle is used for builds by different developers and between local and CI machines.
    >
    >   From now on, you will never invoke Gradle directly; instead, you will use the Gradle wrapper.
  - `settings.gradle`：**存在一个项目的root下**。主要是定义它的子项目；
  - `build.gradle`：**每个子项目都有一个**，定义gradle task；
  - `gradle.properties`：可有可无，进一步定义gradle的行为。比如要不要verbose输出、是否开启缓存；

本工程的主体是父子项目，其中root项目由app、utilities、list三个子项目组成。除此之外，**还有一个root项目[buildSrc](https://docs.gradle.org/current/samples/sample_convention_plugins.html)，用于定义一些插件，约束其他项目的构建行为**。

### [buildSrc](buildSrc/README.md)
单一项目。所以`settings.gradle`和`build.gradle`都定义在它下面。
它存在的意义是[共享子项目之间的构建逻辑](https://docs.gradle.org/current/samples/sample_convention_plugins.html)。详情参考[buildSrc/README.md](buildSrc/README.md)。

### 父子项目
父子项目，子项目由root下的`settings.gradle`定义：
```kotlin
include("app", "list", "utilities")
```

具体参考[app/README.md](app/README.md)

## task
task是gradle最基本的构建逻辑，**相当于maven的plugin的一个goal**。maven的plugin是goal的集合，而gradle的plugin就是task的集合。

比如列出所有的task：
```bash
./gradlew tasks
```
或者针对子项目`app`执行task任务：
```bash
./gradlew :app:tasks
```
或者执行`app`下的`welcome`自定义任务：
```bash
./gradlew :app:welcome
```
task也有参数：
```bash
./gradlew tasks --all
```

task可以定义在`build.gradle`，也可以来自plugin。所以task其实就是maven插件的命令，不过更方便的地方在于，我们可以自定义一个task，更加灵活。

> 这是优于maven的地方，可以定义很多强大的功能，比如check README.md是否存在。**但是要辩证看待所谓“强大”，因为过于灵活会增加对于别人项目的理解成本，maven则相对更好理解一些，因为更“死板”**。
>
> task其实就是为了复用build的逻辑。

### `tasks`变量
`tasks`是一个预定义的变量，代表了构建中所有任务的集合。通过tasks 变量，可以访问、配置和操作构建中的各种任务。这个变量允许你对任务进行各种操作，比如配置任务的行为、依赖关系和执行顺序等。

比如注册任务：
```kotlin
tasks.register("hello") {
    println("Hello!")
}
```

通过名称和类型访问任务，并设置任务细节：
```kotlin
tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
```

通过名称访问任务，并设置细节（环境变量、属性等）：
```kotlin
tasks.named("test").configure {
  systemProperty 'es.insecure_network_trace_enabled', 'true'
  environment 'LANGUAGE', 'en_US'
}
```
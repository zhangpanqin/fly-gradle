## nexts

#### 修改 admin 密码

```shell
# 查看 admin 生成的密码
# docker exec fly-gradle-nexus3 cat /nexus-data/admin.password

bash scripts/change_nexus3_password.sh
```





## Gradle 基础

### Gradle Daemon

为加快项目构建，gralde 会启动一个常驻进程去处理构建。

```shell
# 查看 daemon 运行状态
./gradlew --status
# stop daemon 进程
./gradlew --stop
# 重启 daemon 进程
./gradlew --daemon
```



在项目 `gradle.properties` 下配置启用 gradle daemon。

```properties
org.gradle.caching=true
org.gradle.parallel=true
org.gradle.jvmargs=-Xms512m -Xmx2g -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
```



`Daemon` 默认三小时过期。

当我们经常需要重复构建，跑测试的时候，提高构建速度。



### 目录文件介绍

#### gradle-wrapper.properties

```shell
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-7.6-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

记录了项目配置依赖什么版本的 gradle, 并且记录下载 gradle 之后缓存的目录 （环境变量 GRADLE_USER_HOME）

如果没有配置 `GRADLE_USER_HOME` 默认是 `~/.gradle`



### build.gradle



### Build Script

```groovy
buildscript {
    repositories {
        maven {
            url = "file://$rootDir/maven/releases"
        }
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath 'org.apache.commons:commons-lang3:3.12.0'
    }
}
```

`buildscript` 是定义构建脚本(build.gradle 中代码)的依赖和 repository.





### 生命周期

Gradle 的构建过程都分为三部分：初始化阶段、配置阶段和执行阶段。

#### 初始化阶段

`初始化阶段` gradle 会解析 `setting.gradle` 文件，确定哪些项目需要参与构建，并且为这些项目创建一个对应的 Project 实例。

与 `初始化阶段` 相关的脚本文件是:

-  `settings.gradle` 
- `GRADLE_USER_HOME/init.d/*.gradle`



#### 配置阶段

`配置阶段` 会执行各项目下的 `build.gradle` 脚本，完成Project的配置，并且构造 `Task` 任务依赖关系图。

每个 `build.gralde` 脚本文件对应一个 `Project` 对象。

 `配置阶段` 执行的代码:

- `build.gralde` 中的各种语句、闭包
- `Task` 中的配置段语句



#### 执行阶段

在配置阶段结束后，Gradle会根据任务[Task](https://link.juejin.cn/?target=https%3A%2F%2Fdocs.gradle.org%2Fcurrent%2Fjavadoc%2Forg%2Fgradle%2Fapi%2FTask.html)的依赖关系创建一个有向无环图，可以通过`Gradle`对象的`getTaskGraph`方法访问，对应的类为[TaskExecutionGraph](https://link.juejin.cn/?target=https%3A%2F%2Fdocs.gradle.org%2Fcurrent%2Fjavadoc%2Forg%2Fgradle%2Fapi%2Fexecution%2FTaskExecutionGraph.html)，然后通过调用`gradle <任务名>`执行对应任务。



## 依赖管理

gradle 默认的依赖管理: 当出现依赖冲突的时候，gradle 优先选择版本较高的，因为较高版本会兼容低版本。

```groovy
dependencies {
    implementation 'com.google.guava:guava:31.1-jre'
    implementation 'com.google.code.findbugs:jsr305:3.0.0'

    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
}
```



```shell
# 可以看到 com.google.code.findbugs:jsr305:3.0.0 被 gradle 选择到 com.google.code.findbugs:jsr305:3.0.2
gradle dependency-management:dependencies --configuration compileClasspath
```

```shell
gradle -q dependency-management:dependencyInsight --dependency jsr305 --configuration compileClasspath
```

```text
com.google.code.findbugs:jsr305:3.0.0 (forced)
  Variant compile:
    | Attribute Name                 | Provided | Requested    |
    |--------------------------------|----------|--------------|
    | org.gradle.status              | release  |              |
    | org.gradle.category            | library  | library      |
    | org.gradle.libraryelements     | jar      | classes      |
    | org.gradle.usage               | java-api | java-api     |
    | org.gradle.dependency.bundling |          | external     |
    | org.gradle.jvm.environment     |          | standard-jvm |
    | org.gradle.jvm.version         |          | 17           |

com.google.code.findbugs:jsr305:3.0.0
\--- compileClasspath

com.google.code.findbugs:jsr305:3.0.2 -> 3.0.0
\--- com.google.guava:guava:31.1-jre
     \--- compileClasspath
```

### 依赖冲突

- 禁用依赖传递

guava 不会传递依赖它依赖的库到当前库，可以看到

```text
compileClasspath - Compile classpath for source set 'main'.
+--- com.google.guava:guava:31.1-jre
\--- com.google.code.findbugs:jsr305:3.0.0
```



```txt
dependencies { 
    implementation 'com.google.guava:guava:31.1-jre', {
        transitive = false
    }
    implementation 'com.google.code.findbugs:jsr305:3.0.0'
}
```

- 排除某个依赖

Guava 依赖的别的 jar 可以传递进来，而且排除了 `findbugs`, 项目依赖的版本为 `3.0.0`。

```text
compileClasspath - Compile classpath for source set 'main'.
+--- com.google.guava:guava:31.1-jre
|    +--- com.google.guava:failureaccess:1.0.1
|    +--- com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava
|    +--- org.checkerframework:checker-qual:3.12.0
|    +--- com.google.errorprone:error_prone_annotations:2.11.0
|    \--- com.google.j2objc:j2objc-annotations:1.3
\--- com.google.code.findbugs:jsr305:3.0.0
```



```txt
dependencies { 
	 implementation 'com.google.guava:guava:31.1-jre', {
        exclude group: 'com.google.code.findbugs', module: 'jsr305'
    }
    implementation 'com.google.code.findbugs:jsr305:3.0.0'
}
```

- 强制使用某个版本

```text
+--- com.google.guava:guava:31.1-jre
|    +--- com.google.guava:failureaccess:1.0.1
|    +--- com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava
|    +--- com.google.code.findbugs:jsr305:3.0.2 -> 3.0.0
|    +--- org.checkerframework:checker-qual:3.12.0
|    +--- com.google.errorprone:error_prone_annotations:2.11.0
|    \--- com.google.j2objc:j2objc-annotations:1.3
\--- com.google.code.findbugs:jsr305:3.0.0
```



```txt
dependencies { 
	implementation 'com.google.guava:guava:31.1-jre'
  implementation 'com.google.code.findbugs:jsr305:3.0.0', {
  	force = true 
  } 
}
```

- 引入某个目录下的 jar

```txt
dependencies {
	compile files('lib/hacked-vendor-module.jar')
}
dependencies {
	compile fileTree('lib')
}
```



### 配置依赖之间继承

```txt
configurations {
    integrationTestImplementation.extendsFrom testImplementation
    integrationTestRuntimeOnly.extendsFrom testRuntimeOnly
}

configurations.all {
    resolutionStrategy {
        force 'org.apache.tomcat.embed:tomcat-embed-core:9.0.43'
    }
    exclude group: 'org.slf4j', module: 'slf4j-simple'
}
```



## Plugin

### java-library

```groovy
dependencies {
    api 'org.apache.commons:commons-lang3:3.12.0'
    implementation 'com.google.guava:guava:31.1-jre'
}
```

`commons-lang3` 和 `guava` 都会被传递到引入 jar 包的 `工程 a`，只是二者在 `工程 a` 都会被传递过来，只是二者 scope 不一样。

api 对应 compile，在 `工程 a` 可以直接使用，编译可以通过。

implementation 对应 runtime，编译找不到。



## 开发 Gradle plugin

`mflyyou.maven.publicsh.gradle` 不能有 `plugins{}` 代码块。

Gradle项目有一个相关的ExtensionContainer对象，它包含了应用到项目中的插件的所有设置和属性。你可以通过向容器中添加扩展对象来为你的插件提供配置。

- BuildSrc 下编写的插件，封装统一的构建逻辑，在当前项目下使用。
- 使用单独工程编写插件发布，在别的项目引入共享。
  - 建议用 java 或者 Kotlin 编写二进制插件
- Precompiled script plugins
  - Precompiled script plugin names have two important limitations:
    - They cannot start with `org.gradle`.
    - They cannot have the same name as a built-in plugin id.



## Gradle Task



### 基础 task

```shell
# 查看项目的配置属性
./gradlew properties

# 查看依赖

```



### Task types

[Gradle DSL Version 7.6 Task Types](https://docs.gradle.org/7.6/dsl/index.html#N104E0)



#### 闭包 (closure)

A closure definition follows this syntax:

{ [closureParameters -> ] statements }

- 当方法的最后一个参数是闭包时，可以将闭包放在方法调用之后：

```groovy
{ item++ }                                          

{ -> item++ }                                       

{ println it }                                      

{ it -> println it }                                

{ name -> println name }                            

{ String x, int y ->                                
    println "hey ${x} the value is ${y}"
}

{ reader ->                                         
    def line = reader.readLine()
    line.trim()
}

def closureWithOneArg = { str -> println str }
// 执行闭包
closureWithOneArg("张小凡")



def dependencies(Closure<String> closure) {
    def project = "111"
    println "模拟 gradle 配置"
    closure(project)
    println "模拟 gradle 配置"
}

dependencies({ str -> println str })
dependencies(closureWithOneArg)
// 简化写法
dependencies{ str -> println str }



def task(String name, Closure<String> closure) {
    closure(name)
}

task('aaa', { str -> println(str) })
// 最后一个参数是闭包，可以放到方法小括号外面
task('bbb') { str -> println(str) }
```



#### 定义 Task 带 Type

```groovy
tasks.register("task3", Exec) {
    workingDir "$rootDir"
    commandLine "ls"
}
```



#### 自定义 Task Type

```groovy
class MyTaskType extends DefaultTask {
    @TaskAction
    public void doSelf() {
        println 'MyTaskType action'
    }
}

tasks.register("task4", MyTaskType) {
    doFirst {
        println 'task4 doFirst'
    }
}
```



#### 获取已经定义的 Task

```groovy
tasks.named("task4") {
    doLast {
        println 'task4 doLast'
    }
}

tasks.named("task4").get().doLast {
    println 'task4 doLast'
}
```



#### 传递值到 task 中去

```groovy
public abstract class GreetingTask extends DefaultTask {
    @Input
    public abstract Property<String> getMessage();
    @TaskAction
    public void run() {
    }
}
greeting {
    message = 'plugin test 设置'
}
```

`@Input` 注入值。





### wrapper

- 初始化一个新的 gradle 工程( 这个 gradle 是当前机器安装的 gradle)

```shell
gradle init
```

- 根据项目下的 settings.gradle 或者 build.gradle 生成
  - gradle-wrapper.jar
  - gradle-wrapper.properties
  - gradlew
  - gradlew.bat

```shell
gradle wrapper --gradle-version 7.5.1 --distribution-type bin
```

- 根据项目中的配置生成
  - gradle-wrapper.jar
  - gradle-wrapper.properties
  - gradlew
  - gradlew.bat

```shell
./gradlew wrapper
```

- 升级版本

```shell
./gradlew wrapper --gradle-version 7.6
```


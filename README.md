## nexts

#### 修改 admin 密码

```shell
# 查看 admin 生成的密码
# docker exec fly-gradle-nexus3 cat /nexus-data/admin.password

bash scripts/change_nexus3_password.sh
```



## 目录文件介绍

### gradle-wrapper.properties

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

#### 闭包 (closure)

A closure definition follows this syntax:

{ [closureParameters -> ] statements }

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
```



## 生命周期

Gradle 的构建过程都分为三部分：初始化阶段、配置阶段和执行阶段。

### 初始化阶段

`初始化阶段` gradle 会解析 `setting.gradle` 文件，确定哪些项目需要参与构建，并且为这些项目创建一个对应的 Project 实例。

与 `初始化阶段` 相关的脚本文件是:

-  `settings.gradle` 
- `GRADLE_USER_HOME/init.d/*.gradle`



### 配置阶段

`配置阶段` 会执行各项目下的 `build.gradle` 脚本，完成Project的配置，并且构造 `Task` 任务依赖关系图。

每个 `build.gralde` 脚本文件对应一个 `Project` 对象。

 `配置阶段` 执行的代码:

- `build.gralde` 中的各种语句、闭包
- `Task` 中的配置段语句



### 执行阶段

在配置阶段结束后，Gradle会根据任务[Task](https://link.juejin.cn/?target=https%3A%2F%2Fdocs.gradle.org%2Fcurrent%2Fjavadoc%2Forg%2Fgradle%2Fapi%2FTask.html)的依赖关系创建一个有向无环图，可以通过`Gradle`对象的`getTaskGraph`方法访问，对应的类为[TaskExecutionGraph](https://link.juejin.cn/?target=https%3A%2F%2Fdocs.gradle.org%2Fcurrent%2Fjavadoc%2Forg%2Fgradle%2Fapi%2Fexecution%2FTaskExecutionGraph.html)，然后通过调用`gradle <任务名>`执行对应任务。



## Task









### 依赖冲突

- 禁用以来传递

```txt
dependencies { 
  compile('org.springframework:spring-core:3.0.6') {
  	transitive = false 
  } 
}
```

- 排除某个依赖

```txt
dependencies { 
	compile('org.springframework:spring-core:3.0.6.RELEASE') {
		exclude name: 'commons-logging' 
	}
}
```

- 强制使用某个版本

```txt
dependencies { 
  compile('org.springframework:spring-core:3.0.6.RELEASE') {
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

守护进程

默认三小时过期。

当我们经常需要重复构建，跑测试的时候，提高构建速度

被 UP-TO-DATE 标记的 TASK 意味着被重用了，没有执行跳过了，节省了构建时间

## Gradle Task

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


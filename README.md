## nexts

#### 查看密码

```shell
docker exec fly-gradle-nexus3 cat /nexus-data/admin.password
```

#### Task

- 复制文件

```groovy
task copyPoems(type: Copy) { 
  from 'text-files'
	into 'build/poems'
  exclude '**/*henley*'
  include '**/sh*.txt'
}

task complexCopy(type: Copy) { 
  from('src/main/templates') {
    include '**/*.gtpl'
    into 'templates' 
  }
  from('i18n') 
  from('config') {
    exclude 'Development*.groovy'
    into 'config' 
  }
  into 'build/resources' 
}
```

从一个目录 copy 到另一个目录，可以排除包含某个文件，二者冲突时，exclude 优先级较高。

多个文件来源时

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

### Gradle 命令

```shell
# 根据 settings.gradle 生成 gradle-wrapper.jar gradle-wrapper.properties gradlew, gradlew.bat
gradle wrapper --gradle-version 7.4.2 --distribution-type bin
# task wrapper 可以根据配置生成
./gradlew wrapper
# 升级版本
./gradlew wrapper --gradle-version 7.4.2
```
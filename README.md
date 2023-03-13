## 子项目介绍

- buildSrc 测试封装 fly-gradle 的构建逻辑
- dependency-management 用于测试依赖管理
- task-order 测试 gradle task
- common 用于封装公共 jar，发布到仓库， 用于别的项目使用
- application 用于研究 springboot 插件和 gradle 集成
- binary-plugin 用于测试封装的二进制插件发布到仓库
- script-plugin 用于测试 groovy 写的插件和 precompile script plugin以及发布到仓库
- plugin-test 用于测试 gradle  插件



## includeBuild

复合构建中的插件是可以被子项目使用的，参考 buildSrc





## nexts

#### 修改 admin 密码

```shell
# 查看 admin 生成的密码
# docker exec fly-gradle-nexus3 cat /nexus-data/admin.password

bash scripts/change_nexus3_password.sh
```






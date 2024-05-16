# 定制化`SpringBoot`工程脚手架



## 用途说明

​	基于定制的脚手架快速生成`springboot`工程项目骨架代码和相关基础设施，使用脚手架工程模板主要作用是工程化规格化我们的项目工程，让开发团队都使用一套规约进行工程目录文件的维护。方便开发团队快速创建出统一风格的工程目录和代码模板，此版本基于`SpringBoot 2.6.11`版本，模版继承至h-parent这个父模版，类库的版本统一由h-parent进行维护，在自建模块中尽量少引入额外的类库。



<br/>

## 工程创建

```
mvn archetype:generate -DarchetypeGroupId=com.github.hbq969 -DarchetypeArtifactId=h-archetype -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=com.github.hbq969 -Dversion=1.0-SNAPSHOT -DartifactId=h-example -Dpackage=com.github.hbq969 -Dproduct=code -Dmodule=example -DappPort=30000 -Dauthor=hbq969@gmail.com
```



| **属性**            | **说明**                                                     | **示例**          |
| ------------------- | ------------------------------------------------------------ | ----------------- |
| archetypeGroupId    | 骨架groupId                                                  | com.github.hbq969 |
| archetypeArtifactId | 骨架artifactId                                               | h-archetype       |
| archetypeVersion    | 骨架版本                                                     | 1.0-SNAPSHOT      |
| groupId             | 待创建工程groupId                                            | com.github.hbq969 |
| artifactId          | 待创建工程artifactId<br/>尽量使用${product}-${module}        | h-example         |
| package             | 待创建工程的包名<br/>实际创建后包名<br/>com.github.hbq969.${product}.${module} | com.github.hbq969 |
| product             | 产品名称                                                     | code              |
| module              | 模块名称                                                     | example           |
| version             | 待创建工程版本                                               | 1.0-SNAPSHOT      |
| appPort             | 微服务监听端口<br/>尽量使用30000-32767之间的端口号           | 30000             |
| author              | 作者                                                         | hbq969@gmail.com  |

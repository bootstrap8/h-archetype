基于定制的脚手架快速生成`springboot`工程项目骨架代码和相关基础设施，使用脚手架工程模板主要作用是工程化规格化我们的项目工程，让开发团队都使用一套规约进行工程目录文件的维护。方便开发团队快速创建出统一风格的工程目录和代码模板，此版本基于`SpringBoot 2.7.18`版本，模版继承至h-parent这个父模版，类库的版本统一由h-parent进行维护，在自建模块中尽量少引入额外的类库。

Based on the customized scaffolding, the skeleton code and related infrastructure of the `springboot` project are quickly generated. The main purpose of using the scaffolding project template is to standardize our project engineering and let the development team use a set of specifications to maintain the project directory files. It is convenient for the development team to quickly create a unified style of project directory and code templates. This version is based on the `SpringBoot 2.7.18` version. The template inherits from the parent template h-parent. The version of the class library is uniformly maintained by h-parent. Try to introduce as few additional class libraries as possible in the self-built module.



```bash
git clone https://github.com/hbq969/h-archetype.git
```




```bash
cd h-archetype/target/generated-sources/archetype
mvn install
```




```bash
mvn archetype:generate \
-DinteractiveMode=false \
-DarchetypeGroupId=com.github.hbq969 \
-DarchetypeArtifactId=h-archetype \
-DarchetypeVersion=1.0-SNAPSHOT \
-DgroupId=com.github.hbq969 \
-Dversion=1.0-SNAPSHOT \
-DartifactId=h-example \
-Dpackage=com.github.hbq969 \
-Dproduct=code \
-Dmodule=example \
-DappPort=8080 \
-Dauthor=hbq969@gmail.com
```


|      **属性**       | **说明**                                                     |     **示例**      |
| :-----------------: | :----------------------------------------------------------- | :---------------: |
|  archetypeGroupId   | 骨架groupId                                                  | com.github.hbq969 |
| archetypeArtifactId | 骨架artifactId                                               |    h-archetype    |
|  archetypeVersion   | 骨架版本                                                     |   1.0-SNAPSHOT    |
|       groupId       | 待创建工程groupId                                            | com.github.hbq969 |
|     artifactId      | 待创建工程artifactId<br/>尽量使用code-demo        |     h-example     |
|       package       | 待创建工程的包名<br/>实际创建后包名<br/>com.github.hbq969.code.demo | com.github.hbq969 |
|       product       | 产品名称                                                     |       code        |
|       module        | 模块名称                                                     |      example      |
|       version       | 待创建工程版本                                               |   1.0-SNAPSHOT    |
|       appPort       | 微服务监听端口                                               |       8080        |
|       author        | 作者                                                         | hbq969@gmail.com  |




[hbq969@gmail.com](mailto:hbq969@gmail.com)




The MIT License (MIT)

Copyright (c) 2024 hbq969

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
<p align="center">
	<a href="https://cn.archeruu.com/"><img src="http://124.220.51.225/images/archer/archeruu-logo.png" width="45%"></a>
</p>
<p align="center">
	<strong>🍡Reuse packages of various technology stacks, free from the pain of building wheels.</strong>
</p>

## 🐾简介

Archeruu是一个不断积累的工具库，对各种场景技术栈进行高可用封装，最大限度降低造轮子之苦乏，人生苦短，早点下班！

## 🛠️包含组件

| 模块           | 介绍                             |
| -------------- | -------------------------------- |
| archeruu-bom   | Archeruu模块清单                 |
| archeruu-core  | 核心 |
| archeruu-extra | 拓展                             |
| archeruu-AI    | AI，包括ChatGPT等API操作         |

可以根据需求对每个模块单独引入，也可以通过引入`archeruu-bom`方式引入所有模块。

## 📦安装

### 🍊Maven

在项目的pom.xml的dependencies中加入以下内容:

```xml
<dependency>
    <groupId>com.archeruu</groupId>
    <artifactId>archeruu-bom</artifactId>
    <version>1.0.2</version>
</dependency>
```

### 🍐Gradle

```
implementation 'com.archeruu:archeruu-bom:1.0.2'
```


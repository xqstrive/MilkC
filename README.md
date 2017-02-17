# MilkC

根据java servlet写个一个微小的web框架，主要实现了MVC模式中的控制器（Controller），可以和其他视图和模型框架配合使用。

## 基本原理
很大程度借鉴了python的web框架Django和Flask。使用过滤器FilterDispatcher对url进行分发，通过配置文件注册url。框架主要分为两个层次，一个app层，一个service层，其中：

app层是应用层，将web可以分成不同的模块进行处理；

service是服务层，处理具体的url服务。

本微框架仅提供学习使用，希望能将python框架的优点借鉴过来。

## 使用方法

```java
package app;

import me.xqstrive.milk.module.ModuleService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class ServiceHelloWorld  implements ModuleService {
    public void init() {
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
        try {
            servletResponse.getWriter().write("Hello World!");
        }catch (IOException e){
            System.out.print(e.getMessage());
        }
    }
}
```
上述代码是最简单的“Hello World”实例，服务需要继承me.xqstrive.milk.module.ModuleService接口，框架会首先且仅有一次调用init()方法进行初始化服务，注册到路由分发器中；随后根据url的情况调用不同的service服务。

同时需要在配置文件milk.xml中加入如下代码，对服务ServiceHelloWorld进行注册：
```xml
    <app>
        <app-name>/test</app-name>
        <urls>
            <url>
                <url-name>/helloworld</url-name>
                <url-service>app.ServiceHelloWorld</url-service>
                <url-template></url-template>
            </url>
        </urls>
    </app>
```
访问localhost:8080/test/helloworld即可。

这里需要注意的是，无论是app和是url（service）的注册都是按照顺序匹配，一旦匹配成功不会继续查找。

## 配置文件说明
```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE milk SYSTEM "https://raw.githubusercontent.com/xqstrive/MilkC/master/milk_1_0.dtd">

<milk>
    <static-path></static-path>
    <encoding>UTF-8</encoding>
    <initial-capacity>16</initial-capacity>
    <load-factor>0.75f</load-factor>

    <app>
        <app-name></app-name>
        <urls>
            <url>
                <url-name></url-name>
                <url-service></url-service>
                <url-template></url-template>
            </url>
            <url>
                ...
            </url>
        </urls>
    </app>
    ...
    
</milk>
```
基本属性说明：

| 属性         |            含义 | 默认值             |
|:------------:|:---------------:|:------------------:|
| static-path | 模板路径，可以为相对路径也可为绝对路径        | 空，直接在项目路径下查找|
| encoding     | 编码            | UTF-8              |
| initial-capacity     | Map映射初始大小，同Map初始化中的innitialCapacity            | 16              |
| load-factor  | Map映射中的负载因子，同Map初始化中的loadFactor | 0.75              |

app是对应用层的注册说明，一个web应用可以含有多个app，一个app有一个应用名称和多个url注册表（服务层）。同时url注册表包含本应用层下的url路径，服务，和模板。

在服务层中，路由分发器首先通过url路径调用service服务，若不存在service服务则直接加载template模板；否则根据服务返回相应请求。

## 有待完善

目前主要内容和功能只要这些，下一步准备通过实践完善功能。该框架正在用于搭建自己的个人主页中，希望能经得起考验。

## 联系我

作者是白纸学生党，经验不足，如有有什么问题还希望大神多多提意见。

xqstrive@gmail.com


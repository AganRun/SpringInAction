# Spring In Action

SpringInAction书籍学习

## 第一章 Spring起步

### 1.1 什么是Spring

Spring的核心势提供了一个容器(container),通常称为Spring应用上下文(Spring application context),
他们会创建和管理应用组件。  
这些组件也可以成为bean，会在Spring应用上下文中装配在一起，从而
形成一个完整的应用程序。

把bean装配在一起是通过依赖注入(dependency injection, DI)  
组件不会创建和管理，它依赖的组件的生命周期，使用依赖注入，大家共同依赖于单独的实体（容器）来创建和维护组件。

@Configuration注解会告诉Spring这是一个配置类，会为Spring上下文提供bean。

自动配置起源于**自动装配(Autowiring)**和**组件扫描(component scanning)**
* 借助组件扫描，Spring可以发现路径下的组件，并创建成上下文中的bean
* 借助自动装配，Spring可以自动为组件注入他们依赖的bean

#### 1.3.1 处理web请求

SpringMVC的核心是控制器(controller)的概念。
控制器是处理请求并以某种方式进行消息响应的类。

#### 1.3.3 测试控制器

@WebMvcTest注解。这是Spring Boot 提供的特殊类注解。  
它会让这个测试在Spring MVC的上下文中执行。  
尽管我们可以启动整个服务来测试，但是对于某些场景，只要仿造MVC的运行场景就可以。  
理解：只启动被测试的类注入到容器，然后通过MOCK只向被测试的路径发送请求。


#### 1.3.5 了解Spring Boot DevTools

Spring向开发者提供的工具。例如，代码修改应用自动重启。  

当DevTools运行时，程序被加载到JVM的两个类加载器中。  
**一个类加载器会加载你的代码，这部分内容可能常变。另一个加载器会加载依赖的库**

当探测到变更，DevTools会重新加载包含项目代码的类加载器，重启Spring上下文，这个过程中，
另一个类加载器和JVM不动，减少应用启动的时间。

这个策略的一个不足是，无法反应依赖项的变化。因为依赖项的类加载器不会重启。


### 1.5 小结

* Spring旨在简化开发人员面临的挑战。例如创建web程序，访问数据库
* Spring Boot 建立在Spring之上。简化依赖管理、自动配置、运行时洞察，使Spring更佳易用
* Spring应用上下文中，组件（bean）既可以使用JAVA或XML显示声明，也可以扫描发现，还可以Spring Boot自动配置。

## 第二章 开发web应用

## 2.1 展现信息

Spring提供了多种定义视图的方式，包括JavaServer Pages(JSP)、Thymeleaf、FreeMarker、Mustache和基于Groovy的模板。

使用Thymeleaf
1. 添加Starter启动器。
2. 在运行时，SpringBoot的自动配置发现Thymeleaf在类路径中，因此会为SpringMVC创建支持Thymeleaf视图的bean。

Thymeleaf模板就是增加了一些额外元素属性的HTML。例如"th:each"会迭代一个元素集合。

## 2.3 校验表单输入

有种校验是在方法中添加大量乱七八糟的if/then代码块，但是这样会难以阅读和调试。

Spring支持JAVA的Bean校验API。在SpringBoot项目中，Validation API以及Validation API的Hibernate实现将会作
为SpringBoot web starter的传递性依赖自动添加到项目中。

使用步骤
1. 在要被校验的类上声明校验规则
2. 在控制器方法中声明进行校验
3. 修改表单视图以展示校验错误

复杂的自定义逻辑可以使用正则表达式  
```java
@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
```
## 2.4 视图控制器

定义Controller除了可以通过新建类，添加@controller注解，还可以通过类实现WebMvcConfigure接口实现。
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }
}
```

Thymeleaf作为渲染模板，默认只有第一次使用的时候解析一次，解析的结果会被后续的请求使用。  
这样可以提升性能，对于开发环境，可以通过Spring.thymeleaf.cache=false禁用。

## 第二章小结
* SpringMVC是基于注解的，通过@RequestMapping之类的注解启用请求处理方法的声明。
* SpringMVC支持校验，通过Java Bean Validation API 和 Validation API的实现完成
* Spring支持多种视图方案

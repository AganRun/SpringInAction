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

# 第三章 使用数据

### JDBC 

#### 初始化/预置 SQL

如果在应用的根类路径下存在明为schema.sql的文件，那么在应用启动的时候将会基于数据库执行这个文件中的SQL  
放在src/main/resources下即可。

可能还希望预加载一些数据，可以把SQL命名为data.sql同样放到此目录下

> 注：SpringBoot2.0之后，需要加入```spring.datasource.initialization-mode=always```

#### JDBC save获得ID

- 可以使用带有preparedStatementCreator和KeyHolder的update方法
```java
public int update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder);
```

使用步骤：
1. 先创建一个PreparedStatementCreatorFactory
2. SQL 传递给它，包含参数的类型
3. 调用工厂的newPreparedStatementCreator()
4. 传入对象以及KeyHolder
```java
PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
                "insert into taco (name, createAt) values (?, ?)", Types.CHAR, Types.DATE
        ).newPreparedStatementCreator(
                Arrays.asList(taco.getName(), new Timestamp(taco.getCreateAt().getTime()))
        );
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
```

- 使用SimpleJdbcInsert(相比上一种，极大得简化的代码逻辑)  
它有两个很重要的插入方法，execute()和executeAndReturnKey()  
参数就是一个Map，key为列名，value为值。可以使用Jackson的ObjectMapper的convertValue()方法直接构建出一个Map
```java
// 初始化
this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order_Tacos");

//使用
@SuppressWarnings("unchecked")
Map<String, Object> values = objectMapper.convertValue(order, Map.class);
values.put("placeAt", order.getPlaceAt());  //因为时间会默认转为Long
long orderId = orderInserter.executeAndReturnKey(values).longValue();
```

### JPA

JPA需要实体有一个无参的构造器

#### @PrePersist

createdAt()添加了PrePersist注解，作为了一个**回调方法**  
在持久化之前，会使用这个方法将createdAt方法设置为当前的日期和时间

```java
@Data
@NoArgsConstructor
@Entity
public class Taco {
    private Date createdAt;
    ...
    @PrePersist
    void createAt() {
        this.createdAt = new Date();
    }
}

```

#### JPA方法命名查询

JPA在进行查询方法命名时，会将get、read和find视为同义词，都是用来获取一个或多个实体的  
SpringData会忽略主体中大部分的单词，你尽可以将方法命名为readPuppiesBy...

### SpringBoot启动任务

可以注入CommandLineRunner类及其实现类的Bean，Spring启动时会扫描到这些类，并执行其中的run方法
最简单的Demo，在启动类中加个注入
```java
@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
            }
        };
    }
}
```

如果有多个启动任务，可以创建多个CommandLineRunner的子类  
执行顺序由@Order指定，优先级是按value值从小到大顺序

## 第三章小结

* JdbcTemplate可以简化JDBC的使用
* 当需要知道新生成的ID的值时，可以使用PreparedStatementCreator和KeyHolder
* 简化数据插入===>SimpleJdbcInsert
* JPA极大简化持久化操作，只需编写Repository接口

# 第四章 配置Spring Security

## 初次使用

当加入了starter启动器后，项目启动时，在日志中会出现一个账号及随机密码
```text
Using generated security password: cf18a7e9-ffa6-429f-9331-40d2dd121f53
```
此时登录项目会有一个登录页面，输入账号密码才能访问

security starter的影响
* 所有HTTP请求都需要认证，认证过程是通过HTTP basic认证对话框实现的
* 没有特定的角色及权限，只有一个user用户
* 没有登录页面
 
## 配置Security

只有一个用户显然满足不了需求，security有四种配置用户的方式
* 基于内存的用户存储 （将账号密码直接硬编码到配置代码中，优点：方便快捷，可以用来调试。缺点：项目上线后不方便修改用户）
* 基于JDBC的用户存储
* 以LDAP作为后端的用户存储
* 自定义用户详情服务

### JDBC配置Security

当配置了数据源之后，Spring有一套默认的用户搜索SQL。若表于其不匹配，则可以自定义SQL去配置用户。

酱默认的SQL查询替换为自定义的设计时，很重要的一点是遵循查询的基本协议。**所有查询将用户名作为唯一参数**

```java
@Configuration
@EnableWebSecurity
public class SecurityJdbcConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from Users where username = ?"
                )
                .authoritiesByUsernameQuery(
                            "select username, authority from UserAuthorities where username = ?"
                );
    }
}
```

### 密码加密

数据库明文存储密码是很危险的，passwordEncoder()方法可以接受任意的**PasswordEncoder接口**的实现
- BCryptPasswordEncoder: 使用bcrypt强哈希加密
- NoOpPasswordEncoder: 不进行任何转码(已过期)
- Pbkdf2PasswordEncoder: 使用PBKDF2加密
- SCryptPasswordEncoder: 使用scrypt哈希加密
- StandardPasswordEncoder: 使用SHA-256哈希加密

逻辑是：数据库中的密码应该永远不会被解密。将输入的密码进行算法转码，然后与库中的密码对比。

```java
@Bean
public static PasswordEncoder passwordEncoder() {
    //自定义一个加密流程
    return new PasswordEncoder() {
        @Override
        public String encode(CharSequence charSequence) {
            //加1加密，哈哈
            System.out.println("encode" + charSequence);
            return charSequence.toString() + 1;
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            System.out.println("match,charSequence:" + charSequence + ", dbPassword:" + s);
            return encode(charSequence).equals(s);
        }
    };
}


DB: user1/12341
输入: user1/1234

控制台输出
match,charSequence:1234, dbPassword12341
encode1234
```

### 自定义用户认证

1. 自定义一个实体类，例如User,去实现**UserDetails**接口
2. 定义对应的Repository,Service(需要实现**UserDetailsService的loadUserByUsername(String username)**)
3. 配置类中，auth.userDetailsService(注入的service)

## 保护Web请求

在WebSecurityConfigureAdapter的Configure(HttpSecurity http)方法中
可以配置的功能有：

- 为某个请求提供服务之前，先预先满足条件
- 配置自定义的登录页
- 支持用户退出应用
- 预防跨站请求伪造

### 保护请求 & 登录

可以通过配置路径与对应的角色控制，并指定登录页
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/design", "/orders")
//                    .hasRole("USER")
                .access("hasRole('USER')")     // design 和 orders 路径，需要有user角色
            .antMatchers("/", "/**")
//                    .permitAll();
                .access("permitAll")    //对于/和/**路径不拦截
        .and()
            .formLogin()
                .loginPage("/login")   //登录页面
                .defaultSuccessUrl("/design")  //成功后重定向到design页面
        ;
}
```

存在很多的配置方法，列举其中的几个

| 方法 | 能做什么 |
| --- | --- |
| access(String) | 如果给定的SpEL表达式计算结果为TRUE，就允许访问 |
| authenticated() | 允许认证过的用户访问 |
| denyAll() | 无条件拒绝所有访问 | 
| hasIpAddress(String) | 如果请求来自给定的IP地址，允许访问 | 
| hasRole(String) | 如果用户具备指定的角色，允许访问 |
| not() | 对其他方法的结果求反 | 
| permitAll() | 无条件允许访问 | 
| rememberMe() | 如果用户通过Remember-me认证的，允许访问 | 

SpEl表达式可以编写复杂的逻辑。例如只允许具备ROLE_USER权限的用户，在星期二下午创建新的Taco

### 退出
```java
.and().logout().logoutSuccessUrl("/");
```

### POST请求403?

在上述配置完之后，即使用户已经登录了，在提交订单，请求/design时依旧会被拦截，页面403。
那是因为**SpringSecurity默认是开启了内置的CSRF保护, 建议不要关掉**。先了解一下什么是CSRF。

#### CSRF
跨站请求伪造(Cross-Site Request Forgery, CSRF)。  
它会让用户在一个恶意Web页面填写信息，然后自动将表单以攻击受害者的身份提交到另外一个应用上。
  
可以理解为：攻击者盗用了你的身份，以你的名义发送恶意请求，对服务器来说这个请求是完全合法的，但是却完成了攻击者所期望的一个操作，比如以你的名义发送邮件、发消息，盗取你的账号，添加系统管理员，甚至于购买商品、虚拟货币转账等。 
  
**预防措施** ：应用在展现表单的时候，生成一个CSRF token，放在隐藏域存储起来，在提交表单的时候将token一起发送至服务器，由服务器对比匹配。

可以加入隐藏域（JSP、Thymeleaf默认生成）  
```html
<input type="hidden" name="_csrf" th:value="${_csrf.token}" />
```
提交表单时,使用th:action属性
```html
<form method="POST" th:action="@{/login}" id="loginForm" >
```

## 了解用户是谁

如何获取当前用户的信息
- 注入Principal对象到控制器
- 注入Authentication对象到控制器
- 使用@AuthenticationPricipal注解标注方法
- 使用SecurityContextHolder来获取安全上下文

```java
public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order, Principal principal) {
    String username = principal.getName()   //获取用户的username=>获取用户信息  缺点：业务中会掺杂安全代码

public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order, Authentication authentication) {
    User user = (User) authentication.getPrincipal();       //强转User对象

Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();       //繁琐，但是任何地方都可以，不限制在Controller中

public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order, @AuthenticationPrincipal User user) {
    //使用注解，最方便
}
```

## 第四章小结

- SpringSecurity有自动配置，往往手动配置才能满足需求
- 可以通过多种存储方式配置管理[内存、自定义、LDAP、数据库]
- 自动防范CSRF攻击
- 获取用户信息的多种方式
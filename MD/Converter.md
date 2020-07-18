> 针对SpringInAction第三章,令我发狂的传参

比如现在由一个场景，前端传过来的对象(Thymeleaf渲染，不是前后的分离)是嵌套的。

例如：Order中含有List<Food> foods,而前端传参过来是foods实际上是id集合，**论如何将id自动填充为food对象**。

在这里用一个Car类做演示。POST场景居多，为了方便我这里用里GET

pojo类

```java
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Car {
    String id;
    String name;
}

```
Controller类
```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public class CarController {

    @GetMapping
    public Car car(Car car) {
        System.out.println(car);
        return car;
    }
}
```

Converter类
```java
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarByConverter implements Converter<String, Car> {
    @Override
    public Car convert(String s) {
        return new Car().setId(s).setName("Converter");
    }
}
```

此时的疑惑：匹配条件时什么？所有的String参数都会被转换为Car对象吗？

1. 当访问：```http://localhost:8080/car?id=1```
```java
日志输出 Car(id=1, name=null)
```
发现并没有走converter类转换

2. 当访问 ```http://localhost:8080/car?car=1```
```java
日志输出 Car(id=1, name=Converter)
```
走converter类转换

3. 当访问 ```http://localhost:8080/car?id=1&name=12```
```text
日志输出 Car(id=1, name=12)
```
没有走converter类转换


所以可以看出，当参数类型（定义、传参）的类型匹配上时，会执行到此方法。  
比如定义一个时间类型转换器（long->String），需要DateTime类型的数据，需要参数名为X,传参为long,那么dataX=1592392382932时会被转换。

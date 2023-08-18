# tomato-validator
一个简单的参数验证框架，通过AOP提供对请求参数的校验。

## 如何使用
1. `POM`项目中引入依赖：
    ```xml
    <dependency>
        <groupId>com.xihongshi</groupId>
        <artifactId>tomato-validator</artifactId>
        <version>{tomato.validator.version}</version>
    </dependency>
    ```
2. `application.yml`、`application.yaml`中添加配置：
    ```yaml
    tomato:
        validator:
            enable: true
            fast-validate: true
            default-error-code: 400_000
            default-error-message: 参数验证失败，请检查
    ```
3. 标注启动注解：
    ```java
    @SpringBootApplication
    @EnableTomatoValidator
    public class Application {
        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    }
    ```
## 可配置参数

|       参数名称        |     可选值      |    默认值    |                             说明                             |
| :-------------------: | :-------------: | :----------: | :----------------------------------------------------------: |
|        enable         | **true**、false |     true     |           true：开启参数验证；false：关闭参数验证            |
|     fast-validate     | **true**、false |     true     | true：开启快速验证，当验证不通过时立即返回，不进行后续验证；<br />false：关闭快速验证，当验证不通过时不会立即返回，继续进行后续验证 |
|  default-error-code   |    整型类型     |     400      | 默认的错误代码，在关闭快速验证时，用于验证失败所抛出的异常错误代码 |
| default-error-message |   字符串类型    | 参数验证失败 | 默认的错误消息，在关闭快速验证时，用于验证失败所抛出的异常错误消息 |


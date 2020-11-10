package com.config;

//import io.swagger.annotations.ApiOperation;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Swagger2的接口配置
 *
 * @author zdtx
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {


    @Bean
    public Docket api() {
        return api("默认接口", PathSelectors.any()::apply);
    }

//    @Bean
//    public Docket appApi() {
//        return api("移动端接口", PathSelectors.ant("/app/api/**")::apply);
//    }

    private Docket api(String group, Predicate<String> paths) {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(200).message("请求成功").build());
        responseMessages.add(new ResponseMessageBuilder().code(203).message("认证失败").build());
        responseMessages.add(new ResponseMessageBuilder().code(400).message("请求失败").build());
        responseMessages.add(new ResponseMessageBuilder().code(401).message("鉴权失败").build());
        responseMessages.add(new ResponseMessageBuilder().code(403).message("无权访问").build());
        responseMessages.add(new ResponseMessageBuilder().code(405).message("不支持的请求类型").build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message("服务器异常").build());
        // 可配置token
        Parameter auth = new ParameterBuilder()
                .name("Authorization")
                .description("Token")
                .defaultValue("null")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(false)
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(group)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalOperationParameters(Collections.singletonList(auth))
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(paths::test)
                //            .paths(PathSelectors.none())
                .build();
    }


    private static final String DES = "old-service  接口测试文档，开发环境 \n\r" +
            "\n\r 协作成员列表：\n\r - 谭鑫\n\r" +
            "2020年5月25日11点27分";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("old-service  接口测试文档，开发环境 ")
                .description(DES)
                .version("1.0")
                .contact(
                        new Contact(
                                "star",
                                "null",
                                "null"
                        )
                )
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }


    /**
     * 创建API
     */
//    @Bean
//
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).
//                        paths(PathSelectors.any()).build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("标题：正东通信管理系统_接口文档").
//                description("正东通信管理系统接口文档说明")
//                .version("3.4").build();
//    }
//    @Bean
//    public Docket ProductApi() {
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(productApiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo productApiInfo() {
//        ApiInfo apiInfo = new ApiInfo("正东通信智慧党校系统数据接口文档",
//                "文档描述。。。",
//                "1.0.0",
//                "API TERMS URL",
//                "联系人邮箱",
//                "license",
//                "license url");
//        return apiInfo;
//    }


}

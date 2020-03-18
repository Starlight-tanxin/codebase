package com.zyb.mini.mall.config;

import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
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
 * Swagger Config
 *
 * @author tanxin
 * @version 0.1
 * @date 2019/10/26
 */

@Profile(value = {"dev", "test"})
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return api("default", PathSelectors.any()::apply);
    }

    private Docket api(String group, Predicate<String> paths) {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(200).message("请求成功").build());
        responseMessages.add(new ResponseMessageBuilder().code(203).message("认证失败").build());
        responseMessages.add(new ResponseMessageBuilder().code(400).message("请求失败").build());
        responseMessages.add(new ResponseMessageBuilder().code(401).message("鉴权失败").build());
        responseMessages.add(new ResponseMessageBuilder().code(403).message("无权访问").build());
        responseMessages.add(new ResponseMessageBuilder().code(405).message("不支持的请求类型").build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message("服务器异常").build());

        Parameter auth = new ParameterBuilder()
                .name(RedisKeyNameConstant.AUTH_HEADER)
                .description("Token")
                .defaultValue("oRD4p4_jMlw4PNZXynjwQLGkwPCU")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(true)
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
                .build();
    }


    private static final String DES = "zyb  接口测试文档，开发环境 \n\n" +
            "\n\n 协作成员列表：\n\n - 谭健 \n - Tx\n\n" +
            "©Created by Online zuozuo  2019年10月27日 12:29:28";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("zyb  接口测试文档，开发环境")
                .description(DES)
                .version("2.0")
                .contact(
                        new Contact(
                                "Online zuozuo",
                                "null",
                                "null"
                        )
                )
                .build();
    }

}

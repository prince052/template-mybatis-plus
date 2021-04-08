package com.example.common.configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableKnife4j
@EnableSwagger2WebMvc
public class Swagger2Config {


    @Bean
    public Docket createRestApi() {
        //设置全局响应状态码
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder()
                .code(401)
                .message("token验证失败")
                .responseModel(new ModelRef("Json"))
                .build());//未授权
        responseMessageList.add(new ResponseMessageBuilder()
                .code(403)
                .message("禁止访问")
                .responseModel(new ModelRef("Json"))
                .build());//禁止 — 即使有授权也不需要访问
        responseMessageList.add(new ResponseMessageBuilder()
                .code(404)
                .message("服务器找不到给定的资源")
                .responseModel(new ModelRef("Json"))
                .build());//找不到 — 服务器找不到给定的资源；文档不存在。
        responseMessageList.add(new ResponseMessageBuilder()
                .code(500)
                .message("服务器不能完成请求")
                .responseModel(new ModelRef("Json"))
                .build());// 内部错误 — 因为意外情况，服务器不能完成请求

        return new Docket(DocumentationType.SWAGGER_2)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @Description: TODO(接口的描述配置)
     * @Author: wps
     * @Create 2019/5/30 11:56
     * @Version: V1.0
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Ryan测试接口")
                .description("")
                .contact(new Contact("Ryan", "www.vanke.com", "ryanxu2012@126.com"))
                .version("1.0")
                .build();
    }

}

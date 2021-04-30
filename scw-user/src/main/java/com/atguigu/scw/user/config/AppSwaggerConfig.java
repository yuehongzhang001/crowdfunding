package com.atguigu.scw.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class AppSwaggerConfig {
	@Value("${swagger2.enable:false}")
	private boolean enable = false;

	@Bean("User Module")
	public Docket projectApis() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("User Module").select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.regex("/user.*"))
				.build().apiInfo(apiInfo()).enable(enable);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("scw-system interface document").description("提供用户模块/审核模块/项目模块/支付模块的文档")
				.termsOfServiceUrl("http://www.atguigu.com/").version("1.0").build();
	}
}

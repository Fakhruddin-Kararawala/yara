package com.yara.fieldservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** The Class SwaggerConfig. */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /** Api.
     *
     * @return the docket */
    @Bean
    public Docket api() {
    	return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/fields.*"))
                .build();
    }

    /** Api info.
     *
     * @return the api info */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Yara").description("Senior Software Engineer coding challenge").version("0.0.1-SNAPSHOT").build();
    }
}

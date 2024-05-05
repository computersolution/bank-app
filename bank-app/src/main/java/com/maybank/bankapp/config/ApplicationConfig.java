package com.maybank.bankapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ApplicationConfig class is responsible for configuring Swagger documentation for the REST API.
 */
@Configuration
@EnableSwagger2
public class ApplicationConfig {

    /**
     * Configures the Swagger Docket bean.
     *
     * @return Docket bean for Swagger configuration.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Configures the API information for Swagger documentation.
     *
     * @return ApiInfo object containing API information.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BANKING APPLICATION REST API")
                .description("API for Banking Application.")
                .version("1.0.0")
                .build();
    }
}


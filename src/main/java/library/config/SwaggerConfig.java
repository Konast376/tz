package library.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String BASE_PACKAGE = "java.library";

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(basePackage(BASE_PACKAGE))
                .build()
                .produces(Sets.newHashSet(APPLICATION_JSON_VALUE))
                .consumes(Sets.newHashSet(APPLICATION_JSON_VALUE));
    }
}
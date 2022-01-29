package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc // <=><mvc:annotation-driven/>
// <=> <context:component-scan base-package="controllers"/>
@ComponentScan(basePackages = {"controllers"})
public class WebConfig implements WebMvcConfigurer {

    //<=> <mvc:default-servlet-handler/>
    @Override
    public void configureDefaultServletHandling (
            DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }

    @Bean
    InternalResourceViewResolver getViewResolver () {
        InternalResourceViewResolver resolver = new
                InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp");
        resolver.setRequestContextAttribute("requestContext");
        return resolver;
    }
}
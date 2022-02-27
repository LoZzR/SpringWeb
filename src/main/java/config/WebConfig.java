package config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc // <=><mvc:annotation-driven/>
// <=> <context:component-scan base-package="controllers"/>
@ComponentScan(basePackages = {"controllers", "exceptions"})
//@Import(SpringDataConfig.class)
public class WebConfig implements WebMvcConfigurer {

    //<=> <mvc:default-servlet-handler/>
    /*@Override
    public void configureDefaultServletHandling (
            DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }*/

    @Bean
    InternalResourceViewResolver getViewResolver () {
        InternalResourceViewResolver resolver = new
                InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp");
        //resolver.setRequestContextAttribute("requestContext");
        return resolver;
    }

    @Bean
    HandlerExceptionResolver missingMappingExceptionResolver(){
        SimpleMappingExceptionResolver resolver = new MissingExceptionResolver();
        resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return resolver;
    }

    //<mvc:view-controller path="/" view-name="index"/>
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    //Must be declared before Spring 5
    @Bean
    public MappingJackson2HttpMessageConverter
    mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter
                mappingJackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper());
        return mappingJackson2HttpMessageConverter;
    }

    @Bean
    public MappingJackson2XmlHttpMessageConverter
    mappingJackson2XmlHttpMessageConverter() {
        MappingJackson2XmlHttpMessageConverter
                mappingJackson2XmlHttpMessageConverter =
                new MappingJackson2XmlHttpMessageConverter();
        mappingJackson2XmlHttpMessageConverter.setObjectMapper(xmlMapper());
        return mappingJackson2XmlHttpMessageConverter;
    }


    //Must be declared before Spring 5 or to support modules that provide
    //special converters (@JsonFormat for example)
    @Bean
    public ObjectMapper xmlMapper() {
        ObjectMapper objMapper = new XmlMapper();
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //that is a convenience method that scans and registers all modules in the classpath
        objMapper.findAndRegisterModules();
        return objMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //that is a convenience method that scans and registers all modules in the classpath
        objMapper.findAndRegisterModules();
        return objMapper;
    }
}

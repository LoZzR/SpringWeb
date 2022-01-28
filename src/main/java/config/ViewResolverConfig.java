package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import views.PersonsExcelView;

@Configuration
public class ViewResolverConfig {
    @Bean
    public ViewResolver xlsViewResolver(){
        //interprets a view name as a bean name in the current application context.
        BeanNameViewResolver resolver = new BeanNameViewResolver();
        resolver.setOrder(0);
        return resolver;
    }

   /*<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
     <property name="prefix" value="/WEB-INF/"/>
     <property name="suffix" value=".jsp"/>
    </bean>*/
    @Bean
    ViewResolver jspViewResolver(){
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp" );
        resolver.setOrder(1);
        return resolver;
    }
    @Bean(name="persons/list.xls")
    public View excelView(){
        return new PersonsExcelView();
    }
}

package config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

//<=> web.xml (must be deleted !)
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
        /*implements WebApplicationInitializer*/ {

            //XML mvc config
    /*@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic registration = servletContext.addServlet("main-dispatcher", new DispatcherServlet());
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
        registration.setInitParameter("contextConfigLocation", "/WEB-INF/spring/mvc-config.xml");
    }*/

    //Annotation mvc config
    /*@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic registration = servletContext.addServlet("main-dispatcher", new DispatcherServlet());
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
        registration.setInitParameter("contextConfigLocation", "config.WebConfig");
        registration.setInitParameter("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
    }*/


    /*throw an org.springframework.web.servlet.NoHandlerFoundException when a handler method
    cannot be found, Note that adding this configuration does nothing if DefaultServletHttpRequestHandler is used,*/
    /*@Override
    protected DispatcherServlet createDispatcherServlet (WebApplicationContext servletAppContext) {
        final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }*/

    //same effect as createDispatcherServlet
    /*@Override
    public void customizeRegistration(ServletRegistration.Dynamic
                                              registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }*/

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SecurityConfig.class, SpringDataConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }
}

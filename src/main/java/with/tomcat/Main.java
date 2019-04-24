package with.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*

https://dzone.com/articles/what-is-spring-boot-auto-configuration


org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration,\
org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration,\
org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration,\

 */
public class Main {

    public static void main(String[] args) throws LifecycleException {
        //First it comes here to load Boot Loader Class
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(TomcatConfiguration.class);
    }
}

class TomcatClassPathCondition implements Condition {

    //Because of Conditional Annotation it comes here and validates if tomcat is available on class path.
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        try {
            Class.forName("org.apache.catalina.startup.Tomcat");
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}

class TomcatConfiguration {
    @Bean
    @Conditional(value = TomcatClassPathCondition.class)
    public TomcatLauncher tomcatLauncher() {
        return new TomcatLauncher();
    }
}

class TomcatLauncher {
    @PostConstruct
    public void start() throws LifecycleException {
        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(5050);
        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "helloworldservlet", new HelloWorldServlet());
        context.addServletMappingDecoded("/", "helloworldservlet");
        tomcat.start();

        new Thread(new Runnable() {
            public void run() {
                tomcat.getServer().await();
            }
        }).start();
    }
}

class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Inside My servlet");
        resp.getWriter().println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<p>Welcome to tomcat Embeded Satya</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");
    }

}
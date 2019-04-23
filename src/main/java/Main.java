import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(7070);
        Context context = tomcat.addContext("", null);
        tomcat.addServlet(context, "helloworldservlet", new HelloWorldServlet());
        context.addServletMappingDecoded("/", "helloworldservlet");
        tomcat.start();
        tomcat.getServer().await();
    }
}

class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<p>Welcome to tomcat Embeded</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");
    }

}
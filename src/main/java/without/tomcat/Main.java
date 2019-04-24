package without.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws LifecycleException {
        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(5051);
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
        resp.getWriter().println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<p>Welcome to tomcat Embeded Without Spring</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");
    }

}
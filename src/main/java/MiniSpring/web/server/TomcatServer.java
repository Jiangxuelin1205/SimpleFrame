package MiniSpring.web.server;

import MiniSpring.web.servlet.TestServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {

    private Tomcat tomcat;
    private String[] args;

    public TomcatServer(String[] args) {
        this.args = args;
    }

    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(6699);
        tomcat.start();

        Context context=new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());

        TestServlet servlet=new TestServlet();
        Tomcat.addServlet(context,"testServlet",servlet).setAsyncSupported(true);
        context.addServletMappingDecoded("/test.json","testServlet");

        tomcat.getHost().addChild(context);



        Thread awaitThread = new Thread("await") {
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setDaemon(false);
        awaitThread.start();

    }
}

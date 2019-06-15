package MiniSpring.starter;

import MiniSpring.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

public class MiniApplication {

    public static void run(Class<?> cls,String[] args){
        System.out.println("Hello mini-spring");
        TomcatServer tomcatServer=new TomcatServer(args);
        try {
            tomcatServer.startServer();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}

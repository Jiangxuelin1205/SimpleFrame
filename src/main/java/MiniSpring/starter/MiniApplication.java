package MiniSpring.starter;

import MiniSpring.core.ClassScanner;
import MiniSpring.web.handler.HandlerManager;
import MiniSpring.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.util.Set;

public class MiniApplication {

    public static void run(Class<?> cls, String[] args) {
        System.out.println("Hello mini-spring");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            Set<Class<?>> classes = ClassScanner.scanClass(cls.getPackage().getName());
            HandlerManager.resolvingMapppingHandler(classes);
            classes.forEach(it-> System.out.println(it.getName()));
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}

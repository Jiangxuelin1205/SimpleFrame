package MiniSpringTest.web.server;

import MiniSpring.starter.MiniApplication;

public class Application {

    public static void main(String[] args) {
        System.out.println("Hello world");
        MiniApplication.run(Application.class,args);
    }
}

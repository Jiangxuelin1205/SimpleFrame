package MiniSpringTest.web.handler;

import MiniSpring.core.ClassScanner;
import MiniSpring.web.handler.HandlerManager;
import MiniSpring.web.handler.MappingHandler;
import org.junit.Test;

import java.util.List;
import java.util.Set;

public class HandlerTest {

    @Test
    public void handler() {
        Set<Class<?>> classes = ClassScanner.scanClass("MiniSpringTest.controllers");
        HandlerManager.resolvingMapppingHandler(classes);
        List<MappingHandler> handlers = HandlerManager.getHandlers();
        System.out.println(handlers.size());
    }
}

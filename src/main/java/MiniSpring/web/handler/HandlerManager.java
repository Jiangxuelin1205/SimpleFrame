package MiniSpring.web.handler;

import MiniSpring.web.mvc.Controller;
import MiniSpring.web.mvc.RequestMapping;
import MiniSpring.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HandlerManager {

    private static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    public static void resolvingMapppingHandler(Set<Class<?>> classes) {
        for (Class<?> cls : classes) {
            if (cls.isAnnotationPresent(Controller.class)) {
                parseHandlerFromController(cls);
            }
        }
    }

    private static void parseHandlerFromController(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }
            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
            List<String> parameterName = new ArrayList<>();
            for (Parameter parameter : method.getParameters()) {
                if (!parameter.isAnnotationPresent(RequestParam.class)) {
                    parameterName.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }
            //noinspection ToArrayCallWithZeroLengthArrayArgument
            String[] parameters = parameterName.toArray(new String[parameterName.size()]);
            MappingHandler handler = new MappingHandler(uri, method, cls, parameters);
            HandlerManager.mappingHandlerList.add(handler);
        }
    }

    public static List<MappingHandler> getHandlers() {
        return mappingHandlerList;
    }
}

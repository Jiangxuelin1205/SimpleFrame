package IoC.Context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 能够完成的功能：提供Bean，实例化Bean，保存Bean，
**/
public class IoCContainer {

    private final Map<String,Object> findBeanByName=new ConcurrentHashMap<>();
    private final Map<Class<?>,Object> findBeanByClass=new ConcurrentHashMap<>();

}

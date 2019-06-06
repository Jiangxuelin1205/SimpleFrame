package IoC.Context;

import IoC.Annotations.Autowired;
import IoC.Annotations.Bean;
import IoC.Annotations.Value;
import IoC.Util.PackageScannerException;
import IoC.Util.PacketScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能：提供Bean，实例化Bean，保存Bean，
 **/
public class ApplicationContext implements BeanFactory {

    private String[] basePacket;

    /**
     * 按照Bean的内容存储，Class
     **/
    private final Map<String, Class<?>> types = new ConcurrentHashMap<>();//键为Bean的类型，值为Bean的名称


    @Override
    public Class<?> getBean(String id) {
        return types.get(id);
    }

    @Override
    public Object setBean(String id, Object... parameters) throws CreateBeansException {
        Class bean = types.get(id);
        Object o = null;
        try {
            o = bean.newInstance();
            Field[] fields = bean.getDeclaredFields();
            int i = 0;
            for (Object parameter : parameters) {
                fields[i++].set(o, parameter);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            throw new CreateBeansException(e.getCause());
        }
        return o;
    }


    /**
     * 扫描指定路径下所有的Classes
     * String 为包的路径，形如：Ioc.TestPacket.InnerPacket
     */
    public ApplicationContext(String... basePacket) throws CreateBeansException, PackageScannerException {
        this.basePacket = basePacket;
        refresh();
    }

    /**
     * 获取指定Packet中的类，并将器存储入map中。map的键为类类型，map的值为实例
     **/
    private void refresh() throws PackageScannerException {
        for (String packetName : basePacket) {
            Set<Class<?>> classes = PacketScanner.findClassesWithAnnotations(packetName, Bean.class);
            for (Class type : classes) {
                save(type);
            }
        }
    }

    private void save(Class<?> type) {
        if (type.isAnnotationPresent(Bean.class)) {
            Bean bean = type.getAnnotation(Bean.class);
            types.put(bean.id(), type);
        }
    }

}

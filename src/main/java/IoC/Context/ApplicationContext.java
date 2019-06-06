package IoC.Context;

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
     * 按照Bean的名称存储, String为Bean的名称，Object为Bean的实例
     */
    private final Map<String, Object> names = new ConcurrentHashMap<>();

    /**
     * 按照Bean的内容存储，Class
     **/
    private final Map<Class<?>, Object> types = new ConcurrentHashMap<>();//键为Bean的类型，值为Bean的名称

    @Override
    public Object getBean(String name) {
        return names.get(name);
    }

    @Override
    public <T> Object getBean(Class<T> clazz) {
        return types.get(clazz);
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
    private void refresh() throws CreateBeansException, PackageScannerException {
        for (String packetName : basePacket) {
            Set<Class> classes = PacketScanner.findClassesWithAnnotations(packetName, Bean.class);
            for (Class clazz : classes) {
                createBeans(clazz);
            }
        }
    }

    /**
     *
     * @param beanClass 需要进行实例化的类类型
     */
    private void createBeans(Class beanClass) throws CreateBeansException {
        //如果class已经存在List中,则返回
        if (types.get(beanClass) != null) {
            return;
        }

        try {
            //否则创建class，并且将class放入classesMap中
            @SuppressWarnings("unchecked")
            Constructor constructor = beanClass.getConstructor();
            Object o = constructor.newInstance();
            Field[] fields = beanClass.getDeclaredFields();
            //通过注解初始化参数
            for (Field field : fields) {
                if (field.isAnnotationPresent(Value.class)) {
                    Value value = field.getAnnotation(Value.class);
                    field.setAccessible(true);
                    field.set(o, value.value());
                }
            }
            types.put(beanClass, o);
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new CreateBeansException(e);
        }

    }
}

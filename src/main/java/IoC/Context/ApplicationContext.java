package IoC.Context;

import IoC.Annotations.Bean;
import IoC.Annotations.Value;
import IoC.Util.PackageScannerException;
import IoC.Util.PacketScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 能够完成的功能：提供Bean，实例化Bean，保存Bean，
 **/
public class ApplicationContext implements BeanFactory {

    private Set<String> basePacket;

    //按照Bean的名称存储
    private final Map<String, Object> namesMap = new ConcurrentHashMap<>();//键为Bean的类型名称，值为Bean的实例
    //安装Bean的类型存储
    private final Map<Class<?>, Object> classesMap = new ConcurrentHashMap<>();//键为Bean的类型，值为Bean的名称

    @Override
    public Object getBean(String name) {
        return namesMap.get(name);
    }

    @Override
    public <T> Object getBean(Class<T> clazz) {
        return classesMap.get(clazz);
    }


    /**
     * 扫描指定路径下所有的Classes
     *
     * @param basePacket 每个元素，的包的路径，形如。。。。
     *                   string...              //TODO some work
     */
    public ApplicationContext(Set<String> basePacket) throws CreateBeansException, PackageScannerException {
        this.basePacket = basePacket;
        refresh();
    }

    //遍历basePacket，将basePacket中所有的路径下的带有@Bean的类存储到classes中
    private void refresh() throws CreateBeansException, PackageScannerException {
        for (String packetName : basePacket) {
            List<Class> classes = PacketScanner.findClassesWithAnnotations(packetName, Bean.class);
            for (Class clazz : classes) {
                createBeans(clazz);
            }
        }
    }

    //使用反射初始化类，传入类型，创建实例
    private void createBeans(Class beanClass) throws CreateBeansException {
        //如果class已经存在List中,则返回
        if (classesMap.get(beanClass) != null) {
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
            classesMap.put(beanClass, o);
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new CreateBeansException(e);
        }

    }
}

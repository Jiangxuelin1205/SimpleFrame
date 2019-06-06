package IoC.Context;

public interface BeanFactory {

    /**
     *通过Bean的名称获取Bean
    **/
    Object getBean(String name);

    /**
     * 通过Bean的类型获取Bean
    **/
    <T> Object getBean(Class<T> clazz);
}

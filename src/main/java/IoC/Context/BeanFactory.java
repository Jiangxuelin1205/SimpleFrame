package IoC.Context;

public interface BeanFactory {

    /**
     * 通过Bean的类型获取Bean
     **/
    Class<?> getBean(String id);

    Object setBean(String id,Object... parameters) throws CreateBeansException;
}

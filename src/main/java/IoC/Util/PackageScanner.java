package IoC.Util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class PackageScanner {

    /**
     * 将带有Bean注解的类放入List里
     **/
    /*public static List<Class<?>> findClassesWithAnnotations(String path, Class<?> clazz) {
        List<Class<?>> classesWithAnnotations = new ArrayList<>();
        return classesWithAnnotations;
    }*/

    /**
     * 扫描指定路径,将所有的类放入list里
     **/
    public static void addClasses(File[] files, String packageName,List<Class<?>> classes) throws ClassNotFoundException {
       for(File file:files){
           if(file.isFile()){
               classes.add(Class.forName(packageName+"."+file.getName().substring(file.getName().length()-6)));
           }else{
               addClasses(Objects.requireNonNull(file.listFiles()),packageName+"."+file.getName(),classes);
           }
       }
    }

    /**
     * 加载出某一个指定路径下的所有路径
     **/
    public static File[] directories(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        URL resource=classLoader.getResource(path);
        assert resource != null;
        return new File(resource.getPath()).listFiles();
    }
}

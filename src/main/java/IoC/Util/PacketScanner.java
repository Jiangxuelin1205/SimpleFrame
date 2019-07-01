package IoC.Util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class PacketScanner {

    /**
     * 将带有Bean注解的类放入List里
     **/
    public static Set<Class<?>> findClassesWithAnnotations(String packageName, Class<? extends Annotation> type) throws PackageScannerException {
        Set<Class<?>> classesWithAnnotations = new HashSet<>();
        Set<Class<?>> classes = new HashSet<>();
        addClasses(directories(packageName), packageName, classes);
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(type)) {
                classesWithAnnotations.add(clazz);
            }
        }
        return classesWithAnnotations;
    }

    /**
     * 扫描指定路径,将路径下所有的类放入list里
     **/
    public static void addClasses(File[] files, String packageName, Set<Class<?>> classes) throws PackageScannerException {
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                try {
                    classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    throw new PackageScannerException(e);
                }
            } else {
                addClasses(Objects.requireNonNull(file.listFiles()), packageName + "." + file.getName(), classes);
            }
        }
    }

    /**
     * @param packageName 包名，形如 IoCTest.TestClassPacket.InnerClass
     *                    加载该包下的所有的类和文件
     **/
    public static File[] directories(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        URL resource = classLoader.getResource(path);

        assert resource != null;
        File[] files=new File(resource.getPath()).listFiles();
        for(File file:files){
            System.out.println(file.getAbsolutePath());
        }
        return new File(resource.getPath()).listFiles();
    }
}

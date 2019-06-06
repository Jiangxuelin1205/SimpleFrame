package IoC.Util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PacketScanner {

    //使用set，无序

    /**
     * 将带有Bean注解的类放入List里
     **/
    public static List<Class> findClassesWithAnnotations(String packageName, Class annotation) throws PackageScannerException {
        List<Class> classesWithAnnotations = new ArrayList<>();
        List<Class> classes = new ArrayList<>();
        addClasses(directories(packageName), packageName, classes);
        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(annotation)) {
                classesWithAnnotations.add(clazz);
            }
        }
        return classesWithAnnotations;
    }

    /**
     * 扫描指定路径,将路径下所有的类放入list里
     **/
    public static void addClasses(File[] files, String packageName, List<Class> classes) throws PackageScannerException {
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
     * 加载出某一个指定路径下的所有路径
     **/
    public static File[] directories(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        URL resource = classLoader.getResource(path);
        assert resource != null;
        return new File(resource.getPath()).listFiles();
    }
}

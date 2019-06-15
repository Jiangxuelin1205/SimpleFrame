package MiniSpring.core;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassScanner {

    /**
     * 獲取包名下所有的類名
     * 包名下文件的存在形式可能是jar包，directory，或者.java文件
     **/
    public static List<Class<?>> scanClass(String packageName) throws ClassScannerException {
        List<Class<?>> classes = new ArrayList<>();
        List<File> directory = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                System.out.println(resource.toString());
                URI uri = new URI(resource.toString());
                directory.add(new File(uri.getPath()));
            }
            for (File file : directory) {
                classes.addAll(addFiles(file, packageName));
            }
        } catch (IOException | URISyntaxException | ClassNotFoundException e) {
            throw new ClassScannerException(e.getMessage());
        }
        return classes;
    }

    private static List<Class<?>> addFiles(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> list = new ArrayList<>();
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                list.addAll(addFiles(file, packageName + "." + file.getName()));
            } else {
                list.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return list;
    }
}

package MiniSpring.core;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {

    /**
     * 獲取包名下所有的類名
     * 包名下文件的存在形式可能是jar包，directory，或者.java文件
     **/
    public static Set<Class<?>> scanClass(String packageName) throws ClassScannerException {
        Set<Class<?>> classes = new HashSet<>();
        List<File> directory = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                URI uri = new URI(resource.toString());
                if (resource.getProtocol().endsWith(".jar")) {
                    //todo
                }
                directory.add(new File(uri.getPath()));
            }
            for (File file : directory) {
                classes.addAll(addClassesFromDirectory(file, packageName));
            }
        } catch (IOException | URISyntaxException | ClassNotFoundException e) {
            throw new ClassScannerException(e.getMessage());
        }
        return classes;
    }

    private static Set<Class<?>> addClassesFromDirectory(File directory, String packageName) throws ClassNotFoundException {
        Set<Class<?>> result = new HashSet<>();
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                result.addAll(addClassesFromDirectory(file, packageName + "." + file.getName()));
            } else {
                result.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return result;
    }

    private static Set<Class<?>> addClassesFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jars = jarFile.entries();
        while (jars.hasMoreElements()) {
            JarEntry entry = jars.nextElement();
            String entryName = entry.getName();
            if (entryName.endsWith(".class") && entryName.startsWith(path)) {
                String classFullName = entryName.replace("/", ".").substring(0, entryName.length() - 6);
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}

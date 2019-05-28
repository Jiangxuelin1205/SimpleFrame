
import ClazzLoader.MyClassLoader;
import org.junit.Test;

public class ClassLoaderTest {

    @Test
    public void test_find_class() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String path = "G:\\IntelleJIdeaWorkingPlace\\SimpleFrame\\";
        String name = "TestClassLoader";
        MyClassLoader myClassLoader=new MyClassLoader(path);
        Class myClass=myClassLoader.loadClass(name);
        Object o=myClass.newInstance();
    }
}

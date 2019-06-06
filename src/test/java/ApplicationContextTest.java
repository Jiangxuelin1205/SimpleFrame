import IoC.Annotations.Bean;
import IoC.Context.ApplicationContext;
import IoC.Context.CreateBeansException;
import IoC.Util.PackageScannerException;
import IoC.Util.PacketScanner;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ApplicationContextTest {

    @Test
    public void add_directories() {
        File[] files = PacketScanner.directories("TestClassPacket");
        String[] paths = new String[files.length];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = files[i].getPath();
        }
        String[] expectedFiles = new String[]{
                "G:\\IntelleJIdeaWorkingPlace\\SimpleFrame\\target\\test-classes\\TestClassPacket\\Dog.class",
                "G:\\IntelleJIdeaWorkingPlace\\SimpleFrame\\target\\test-classes\\TestClassPacket\\InnerPacket",
                "G:\\IntelleJIdeaWorkingPlace\\SimpleFrame\\target\\test-classes\\TestClassPacket\\Person.class"
        };
        Assert.assertArrayEquals(expectedFiles, paths);
    }

    @Test
    public void add_classes() throws PackageScannerException {
        //given
        Set<Class> types = new HashSet<>();
        //when
        File[] files = PacketScanner.directories("TestClassPacket");
        PacketScanner.addClasses(files, "TestClassPacket", types);
        //then
        Assert.assertTrue(types.contains(TestClassPacket.Dog.class));
        Assert.assertTrue(types.contains(TestClassPacket.InnerPacket.Cat.class));
        Assert.assertTrue(types.contains(TestClassPacket.InnerPacket.Jam.class));
        Assert.assertTrue(types.contains(TestClassPacket.Person.class));
        Assert.assertEquals(types.size(), 4);
    }

    @Test
    public void find_class_with_annotations() throws PackageScannerException {
        Set<Class> classesWithAnnotations = PacketScanner.findClassesWithAnnotations("TestClassPacket", Bean.class);
        Assert.assertTrue(classesWithAnnotations.contains(TestClassPacket.InnerPacket.Cat.class));
        Assert.assertTrue(classesWithAnnotations.contains(TestClassPacket.Dog.class));
        Assert.assertTrue(classesWithAnnotations.contains(TestClassPacket.Person.class));
        Assert.assertEquals(classesWithAnnotations.size(), 3);
    }

    @Test
    public void refresh_test() throws CreateBeansException, PackageScannerException {
        ApplicationContext applicationContext = new ApplicationContext("TestClassPacket");
    }

    @Test
    public void create_bean_test() throws CreateBeansException, PackageScannerException {
        ApplicationContext applicationContext = new ApplicationContext("TestClassPacket");
    }
}

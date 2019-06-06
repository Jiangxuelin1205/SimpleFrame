import IoC.Annotations.Bean;
import IoC.Context.ApplicationContext;
import IoC.Context.CreateBeansException;
import IoC.Util.PackageScannerException;
import IoC.Util.PacketScanner;
import TestClassPacket.InnerPacket.Cat;
import TestClassPacket.TestClass;
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
                "G:\\IntelleJIdeaWorkingPlace\\SimpleFrame\\target\\test-classes\\TestClassPacket\\Person.class",
                "G:\\IntelleJIdeaWorkingPlace\\SimpleFrame\\target\\test-classes\\TestClassPacket\\TestClass.class"

        };
        Assert.assertArrayEquals(expectedFiles, paths);
    }

    @Test
    public void add_classes() throws PackageScannerException {
        //given
        Set<Class<?>> types = new HashSet<>();
        //when
        File[] files = PacketScanner.directories("TestClassPacket");
        PacketScanner.addClasses(files, "TestClassPacket", types);
        //then
        Assert.assertTrue(types.contains(TestClassPacket.Dog.class));
        Assert.assertTrue(types.contains(TestClassPacket.InnerPacket.Cat.class));
        Assert.assertTrue(types.contains(TestClassPacket.InnerPacket.Jam.class));
        Assert.assertTrue(types.contains(TestClassPacket.Person.class));
        Assert.assertTrue(types.contains(TestClass.class));
        Assert.assertEquals(types.size(), 5);
    }

    @Test
    public void find_class_with_annotations() throws PackageScannerException {
        Set<Class<?>> classesWithAnnotations = PacketScanner.findClassesWithAnnotations("TestClassPacket", Bean.class);
        Assert.assertTrue(classesWithAnnotations.contains(TestClassPacket.InnerPacket.Cat.class));
        Assert.assertTrue(classesWithAnnotations.contains(TestClassPacket.Dog.class));
        Assert.assertTrue(classesWithAnnotations.contains(TestClassPacket.Person.class));
        Assert.assertTrue(classesWithAnnotations.contains(TestClass.class));
        Assert.assertEquals(classesWithAnnotations.size(), 4);
    }

    @Test
    public void refresh_test() throws CreateBeansException, PackageScannerException {
        ApplicationContext applicationContext = new ApplicationContext("TestClassPacket");
        Cat cat = (Cat) applicationContext.getBean(Cat.class);
        Assert.assertEquals(cat.name, "ctct");
        Assert.assertEquals(cat.roar, "niangniang");
    }
}

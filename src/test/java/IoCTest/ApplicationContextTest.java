package IoCTest;

import IoC.Annotations.Bean;
import IoC.Context.ApplicationContext;
import IoC.Context.CreateBeansException;
import IoC.Util.PackageScannerException;
import IoC.Util.PacketScanner;
import IoCTest.TestClassPacket.InnerPacket.Cat;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ApplicationContextTest {

    @Test
    public void add_directories() {
        File[] files = PacketScanner.directories("IoCTest.TestClassPacket");
        String[] paths = new String[files.length];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = files[i].getPath();
        }
        String[] expectedFiles = new String[]{
                "G:\\IntelleJIdeaWorkingPlace\\SimpleFrame\\target\\test-classes\\IoCTest\\TestClassPacket\\Dog.class",
                "G:\\IntelleJIdeaWorkingPlace\\SimpleFrame\\target\\test-classes\\IoCTest\\TestClassPacket\\InnerPacket",
                "G:\\IntelleJIdeaWorkingPlace\\SimpleFrame\\target\\test-classes\\IoCTest\\TestClassPacket\\Person.class",
                "G:\\IntelleJIdeaWorkingPlace\\SimpleFrame\\target\\test-classes\\IoCTest\\TestClassPacket\\TestClass.class"

        };
        Assert.assertArrayEquals(expectedFiles, paths);
    }

    @Test
    public void add_classes() throws PackageScannerException {
        //given
        Set<Class<?>> types = new HashSet<>();
        //when
        File[] files = PacketScanner.directories("IoCTest.TestClassPacket");
        PacketScanner.addClasses(files, "IoCTest.TestClassPacket", types);
        //then
        Assert.assertTrue(types.contains(IoCTest.TestClassPacket.Dog.class));
        Assert.assertTrue(types.contains(IoCTest.TestClassPacket.InnerPacket.Cat.class));
        Assert.assertTrue(types.contains(IoCTest.TestClassPacket.InnerPacket.Jam.class));
        Assert.assertTrue(types.contains(IoCTest.TestClassPacket.Person.class));
        Assert.assertTrue(types.contains(IoCTest.TestClassPacket.TestClass.class));
        Assert.assertEquals(types.size(), 5);
    }

    @Test
    public void find_class_with_annotations() throws PackageScannerException {
        Set<Class<?>> classesWithAnnotations = PacketScanner.findClassesWithAnnotations("IoCTest.TestClassPacket", Bean.class);
        Assert.assertTrue(classesWithAnnotations.contains(IoCTest.TestClassPacket.InnerPacket.Cat.class));
        Assert.assertTrue(classesWithAnnotations.contains(IoCTest.TestClassPacket.Dog.class));
        Assert.assertTrue(classesWithAnnotations.contains(IoCTest.TestClassPacket.Person.class));
        Assert.assertTrue(classesWithAnnotations.contains(IoCTest.TestClassPacket.TestClass.class));
        Assert.assertEquals(classesWithAnnotations.size(), 4);
    }

    @Test
    public void refresh_test() throws PackageScannerException, CreateBeansException {
        ApplicationContext applicationContext = new ApplicationContext("IoCTest.TestClassPacket");
        Object[] parameters=new Object[]{
                "niang","ctct"
        };
        Cat cat= (Cat) applicationContext.setBean("Cat",parameters);
    }

}

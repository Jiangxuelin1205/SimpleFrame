import IoC.Annotations.Bean;
import IoC.Context.ApplicationContext;
import IoC.Context.CreateBeansException;
import IoC.Util.PackageScannerException;
import IoC.Util.PacketScanner;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApplicationContextTest {

    @Test
    public void add_directories() {
        File[] files = PacketScanner.directories("IoC.TestClassPacket");
        Assert.assertEquals(files.length, 3);
    }

    @Test
    public void add_classes() throws PackageScannerException {
        File[] files = PacketScanner.directories("IoC.TestClassPacket");
        List<Class> classes = new ArrayList<>();
        PacketScanner.addClasses(files, "IoC.TestClassPacket", classes);
        Assert.assertEquals(classes.size(), 4);
    }

    @Test
    public void find_class_with_annotations() throws PackageScannerException {
        List<Class> classesWithAnnotations = PacketScanner.findClassesWithAnnotations("IoC.TestClassPacket", Bean.class);
        Assert.assertEquals(classesWithAnnotations.size(), 3);
//        Assert.assertEquals();
    }

    @Test
    public void refresh_test() throws CreateBeansException, PackageScannerException {
        Set<String> packets = new HashSet<>();
        packets.add("IoC.TestClassPacket");
        ApplicationContext applicationContext = new ApplicationContext(packets);
    }

    @Test
    public void create_bean_test() throws CreateBeansException, PackageScannerException {
        Set<String> packets = new HashSet<>();
        packets.add("IoC.TestClassPacket");
        ApplicationContext applicationContext = new ApplicationContext(packets);
    }
}

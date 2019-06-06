import IoC.Annotations.Bean;
import IoC.Util.Packetcanner;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IoCContainerTest {

    @Test
    public void add_directories() {
        File[] files = Packetcanner.directories("IoC.TestClassPacket");
        Assert.assertEquals(files.length, 3);
    }

    @Test
    public void add_classes() throws ClassNotFoundException {
        File[] files = Packetcanner.directories("IoC.TestClassPacket");
        List<Class<?>> classes = new ArrayList<>();
        Packetcanner.addClasses(files, "IoC.TestClassPacket", classes);
        Assert.assertEquals(classes.size(), 3);
    }

    @Test
    public void find_class_with_annotations() throws ClassNotFoundException {
        List<Class<?>> classesWithAnnotations=Packetcanner.findClassesWithAnnotations("IoC.TestClassPacket",Bean.class);
        Assert.assertEquals(classesWithAnnotations.size(),3);
    }
}

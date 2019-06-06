import IoC.Util.PackageScanner;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IoCContainerTest {

    @Test
    public void add_directories() {
        File[] files = PackageScanner.directories("IoC.TestClassPacket");
        Assert.assertEquals(files.length, 3);
    }

    @Test
    public void add_classes() throws ClassNotFoundException {
        File[] files = PackageScanner.directories("IoC.TestClassPacket");
        List<Class<?>> classes = new ArrayList<>();
        PackageScanner.addClasses(files, "IoC.TestClassPacket", classes);
        Assert.assertEquals(classes.size(), 3);
    }
}

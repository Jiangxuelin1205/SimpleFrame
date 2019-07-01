package MiniSpringTest.core;

import MiniSpring.core.ClassScanner;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class ClassScannerTest {

    @Test
    public void scan_packet() {
        Set<Class<?>> classes = ClassScanner.scanClass("MiniSpringTest.controllers");
        Assert.assertTrue(classes.contains(MiniSpringTest.controllers.SalaryController.class));
        Assert.assertTrue(classes.contains(MiniSpringTest.controllers.innerController.AgeController.class));
    }
}

package MiniSpringTest.core;

import MiniSpring.core.ClassScanner;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ClassScannerTest {
    @Test
    public void scan_packet() throws IOException, URISyntaxException {
        List<Class<?>> classes = ClassScanner.scanClass("MiniSpringTest.controllers");

    }
}

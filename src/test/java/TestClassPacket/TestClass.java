package TestClassPacket;

import IoC.Annotations.Bean;
import IoC.Annotations.Value;

@Bean
public class TestClass {

    @Value("test")
    String name;
}

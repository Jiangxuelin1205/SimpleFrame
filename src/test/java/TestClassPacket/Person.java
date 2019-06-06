package TestClassPacket;

import IoC.Annotations.Bean;
import IoC.Annotations.Value;

@Bean
public class Person {

    @Value(value="12")
    private String age;

    @Value(value="urk")
    private String name;
}

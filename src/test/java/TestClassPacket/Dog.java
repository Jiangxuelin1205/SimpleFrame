package TestClassPacket;

import IoC.Annotations.Bean;
import IoC.Annotations.Value;

@Bean(id="Dog")
public class Dog {

    private String roar;

    private String name;
}

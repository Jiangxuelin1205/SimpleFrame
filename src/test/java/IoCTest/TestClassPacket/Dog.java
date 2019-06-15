package IoCTest.TestClassPacket;

import IoC.Annotations.Bean;

@Bean(id="Dog")
public class Dog {

    private String roar;

    private String name;
}

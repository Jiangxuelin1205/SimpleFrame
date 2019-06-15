package IoCTest.TestClassPacket.InnerPacket;

import IoC.Annotations.Bean;

@Bean(id = "Cat")
public class Cat {

    public String roar;

    public String name;
}

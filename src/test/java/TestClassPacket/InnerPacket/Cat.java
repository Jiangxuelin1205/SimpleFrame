package TestClassPacket.InnerPacket;

import IoC.Annotations.Bean;
import IoC.Annotations.Value;

@Bean(id = "Cat")
public class Cat {

    public String roar;

    public String name;
}

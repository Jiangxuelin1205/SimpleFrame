package TestClassPacket.InnerPacket;

import IoC.Annotations.Bean;
import IoC.Annotations.Value;

@Bean
public class Cat {

    @Value(value = "niangniang")
    public String roar;

    @Value(value = "ctct")
    public String name;
}

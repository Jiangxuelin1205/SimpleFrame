package IoC.TestClassPacket.InnerPacket;

import IoC.Annotations.Bean;
import IoC.Annotations.Value;

@Bean
public class Cat {

    @Value(value="niangniang")
    private String roar;

    @Value(value = "ctct")
    private String name;
}

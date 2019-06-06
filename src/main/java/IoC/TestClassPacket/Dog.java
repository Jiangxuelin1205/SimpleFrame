package IoC.TestClassPacket;

import IoC.Annotations.Bean;
import IoC.Annotations.Value;

@Bean
public class Dog {

    @Value(value="baubau")
    private String roar;

    @Value(value = "dqdq")
    private String name;
}

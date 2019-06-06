package TestClassPacket;

import IoC.Annotations.Autowired;
import IoC.Annotations.Bean;
import IoC.Annotations.Value;


@Bean(id="Person")
public class Person {

    /*@Autowired
    private TestClass testClass;*/

    private String age;

    private String name;
}

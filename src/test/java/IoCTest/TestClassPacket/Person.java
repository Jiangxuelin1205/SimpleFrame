package IoCTest.TestClassPacket;

import IoC.Annotations.Bean;


@Bean(id="Person")
public class Person {

    /*@Autowired
    private TestClass testClass;*/

    private String age;

    private String name;
}

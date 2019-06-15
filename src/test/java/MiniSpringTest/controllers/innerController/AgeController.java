package MiniSpringTest.controllers.innerController;

import MiniSpring.web.mvc.Controller;
import MiniSpring.web.mvc.RequestMapping;
import MiniSpring.web.mvc.RequestParam;

@Controller
public class AgeController {

    @RequestMapping("/age.json")
    public void getAge(@RequestParam("name") String name, @RequestParam("age") String age) {
        System.out.println(name + " is " + age + " years old.");
    }
}

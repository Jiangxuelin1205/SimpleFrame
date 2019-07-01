package MiniSpringTest.controllers;

import MiniSpring.web.mvc.Controller;
import MiniSpring.web.mvc.RequestMapping;
import MiniSpring.web.mvc.RequestParam;

@Controller
public class SalaryController {

    @RequestMapping("salary.json")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience) {
        return 10000;
    }
}

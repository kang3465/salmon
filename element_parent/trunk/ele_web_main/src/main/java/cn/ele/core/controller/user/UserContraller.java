package cn.ele.core.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserContraller {
    /*@Autowired
    UserService userservice;*/
    @RequestMapping("Login")
    public String Login(){
        return "success";
    }

}

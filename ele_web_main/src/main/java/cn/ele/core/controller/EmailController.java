package cn.ele.core.controller;

import cn.ele.core.pojo.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailController {

    @RequestMapping("subscriptionByEmail")
    public Result subscriptionByEmail(String email){




        return null;
    }

}

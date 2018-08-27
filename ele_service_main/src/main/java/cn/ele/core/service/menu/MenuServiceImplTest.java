package cn.ele.core.service.menu;

import cn.ele.core.pojo.user.Menu;
import cn.ele.core.pojo.user.User;
import cn.ele.core.service.MenuService;
import cn.ele.core.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-*.xml" })
public class MenuServiceImplTest {
/*
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(MenuServiceImpl.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "spring/applicationContext-*.xml");
    }*/
    @Autowired
    MenuService menuService;
    @Autowired
    UserService userService;
    @Test
    public void queryMenuByUser() throws Exception {
        User user = userService.findOneByUserName("kang3464");
        if (user!=null) {
            List<Menu> menuList = menuService.queryMenuByUser(user);
            System.out.println(menuList);
        }else {
            System.out.println("caonima");
        }

    }
}

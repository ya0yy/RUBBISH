package com.yaoyyy.rubbish.authserver;

import com.yaoyyy.rubbish.common.util.SpringContextUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Oauth2ServerApplicationTests {

    @Test
    public void contextLoads() {
        ApplicationContext context = SpringContextUtils.getApplicationContext();
        ArrayList<String> strings = new ArrayList<String>() {{
            add("21");
        }};
        System.out.println(strings);
    }

}

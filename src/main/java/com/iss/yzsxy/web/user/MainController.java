package com.iss.yzsxy.web.user;

import com.iss.yzsxy.tools.Tools;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gaowenfeng on 2017/5/28.
 */
@Controller
@Scope("prototype")
public class MainController {

    @RequestMapping("/")
    public String main(){
        String result = null;
        Integer role = Integer.parseInt(Tools.obtainPrincipal().getRoles());
        System.out.println(role);
        switch (role){
            case 0:
                result = "admin";
                break;
            case 1:
                result = "admin";
                break;
            case 2:
                result = "myTitle";
                break;
            case 3:
                result = "title";
                break;
        }
        return result;
    }

}

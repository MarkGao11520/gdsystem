package com.iss.yzsxy.web.user;

import com.iss.yzsxy.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaowenfeng on 2017/5/28.
 */
@Controller
@Scope("prototype")
public class MainController {

    @Autowired
    private ResourceLoader resourceLoader;

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

    @Value("${myTemp.rootPath}")
    private String rootPath;

    @RequestMapping("/file/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable String filename, HttpServletRequest request){
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + rootPath+ filename.toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}

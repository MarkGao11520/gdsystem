package com.iss.yzsxy.web.classs;

import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.pojo.classs.ClassDto;
import com.iss.yzsxy.pojo.classs.Classs;
import com.iss.yzsxy.service.classs.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by gaowenfeng on 2017/6/1.
 */
@Controller
@RequestMapping("/classController")
@Scope("prototype")
public class ClassController {
    @Autowired
    IClassService iClassService;


    @RequestMapping("/getClasses")
    @ResponseBody
    public PageInfo<Classs> classsSelectList(ClassDto classDto){
        return iClassService.obtainClassList(classDto);
    }

    @RequestMapping("/classesDelete")
    @ResponseBody
    public boolean classsDelete(@RequestBody Integer[] classids){
        return iClassService.classDelete(classids);
    }



    @RequestMapping("/classesUplate")
    @ResponseBody
    public boolean classsUplate(Classs classes){
        return iClassService.classUpdate(classes);
    }

    @RequestMapping("/classesAdd")
    @ResponseBody
    public int classsAdd(Classs classes){
        return iClassService.classAdd(classes);
    }

    @RequestMapping("/getClassKVList")
    @ResponseBody
    public List<Classs> getClassKVList(){
        return iClassService.obtainClassKVList();
    }
}

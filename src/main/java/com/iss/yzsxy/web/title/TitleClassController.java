package com.iss.yzsxy.web.title;

import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.pojo.classs.ClassDto;
import com.iss.yzsxy.pojo.classs.Classs;
import com.iss.yzsxy.pojo.title.TitleClass;
import com.iss.yzsxy.pojo.title.TitleClassDto;
import com.iss.yzsxy.service.classs.IClassService;
import com.iss.yzsxy.service.title.ITitleClassService;
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
@RequestMapping("/titleClassController")
@Scope("prototype")
public class TitleClassController {
    @Autowired
    ITitleClassService iTitleClassService;


    @RequestMapping("/getList")
    @ResponseBody
    public PageInfo<TitleClass> classsSelectList(TitleClassDto dto){
        return iTitleClassService.obtainList(dto);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean classsDelete(@RequestBody Integer[] ids){
        return iTitleClassService.delete(ids);
    }



    @RequestMapping("/update")
    @ResponseBody
    public boolean classsUplate(TitleClass obj){
        return iTitleClassService.update(obj);
    }

    @RequestMapping("/add")
    @ResponseBody
    public int classsAdd(TitleClass obj){
        return iTitleClassService.add(obj);
    }

    @RequestMapping("/getKVList")
    @ResponseBody
    public List<TitleClass> getClassKVList(){
        return iTitleClassService.obtainKVList();
    }
}

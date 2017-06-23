package com.iss.yzsxy.web.title;

import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.pojo.title.Title;
import com.iss.yzsxy.pojo.title.TitleDto;
import com.iss.yzsxy.service.title.ITitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by gaowenfeng on 2017/6/1.
 */
@Controller
@RequestMapping("/titleController")
@Scope("prototype")
public class TitleController {
    @Autowired
    ITitleService iTitleService;


    @RequestMapping("/getList")
    @ResponseBody
    public PageInfo<Title> classsSelectList(TitleDto dto){
        return iTitleService.obtainList(dto);
    }

    @RequestMapping("/getKvList")
    @ResponseBody
    public List<Title> getKvList(Integer titleclassid){
        return iTitleService.obtainEchartList(titleclassid);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean classsDelete(@RequestBody Integer[] ids){
        return iTitleService.delete(ids);
    }



    @RequestMapping("/update")
    @ResponseBody
    public boolean classsUplate(Title obj){
        return iTitleService.update(obj);
    }

    @RequestMapping("/add")
    @ResponseBody
    public int classsAdd(Title obj){
        return iTitleService.add(obj);
    }

    @RequestMapping("/studentaddtitle")
    @ResponseBody
    public int studentaddtitle(Title obj){
        return iTitleService.studentaddtitle(obj);
    }

    @RequestMapping("/getCountMap")
    @ResponseBody
    public Map<String,Object>  getCountMap(){
        return iTitleService.obtainCountMap();
    }

}

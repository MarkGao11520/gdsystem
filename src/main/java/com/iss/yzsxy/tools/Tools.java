package com.iss.yzsxy.tools;

import com.iss.yzsxy.pojo.user.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gaowenfeng on 2017/4/25.
 */
public class Tools {

    /**
     *
     * @param request   获取地址用的
     * @param uploadPath   具体路径
     * @param multipartFile   上传的文件
     * @return
     */
    public static Map<String,Object> uploadFile(HttpServletRequest request,String uploadPath,MultipartFile multipartFile){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            UUID uuid = UUID.randomUUID();
            String path = request.getServletContext().getRealPath(
                    "/");
            String userPath = "/" + uploadPath+"/";
            String realPath = path + userPath ;
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filename = uuid.toString()+"."+multipartFile.getContentType().split("/")[1].toString();
            String fileurl = realPath+filename;
            multipartFile.transferTo(new File(fileurl));
            map.put("isSuccess",true);
            map.put("url",userPath.substring(1,userPath.length())+filename);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isSuccess",false);
            map.put("url","");
        }finally {
            return map;
        }
    }

    public static SysUser obtainPrincipal(){
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        SysUser sysUser = new SysUser();
        if (principal instanceof UserDetails) {
            sysUser = (SysUser) principal;
        }
        return sysUser;
    }

    public static String dataLongToString(long time){
        Date dt=new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(dt);
    }

    public static Long dataStringToLong(String time) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt=sdf.parse(time);
            return dt.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Long obtianTodayTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(date)+" 00:00:00";
        try {
            Date date1 = sdf1.parse(s);
            return date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String filterHtml(String html){
        String p = "<.?>|\\n";
        String p2 = "</.?>";
        String p3 = "&nbsp;";
        String word;
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(html);
        word = matcher.replaceAll("");

        Pattern pattern2 = Pattern.compile(p2);
        Matcher matcher2 = pattern2.matcher(word);
        word = matcher2.replaceAll("\n");

        Pattern pattern3 = Pattern.compile(p3);
        Matcher matcher3 = pattern3.matcher(word);
        word = matcher3.replaceAll(" ");
        return word;
    }
}

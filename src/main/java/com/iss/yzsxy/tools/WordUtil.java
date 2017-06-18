package com.iss.yzsxy.tools;

/**
 * Created by gaowenfeng on 2017/5/31.
 */

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 适用于word 2007
 * poi版本 3.7
 * @author Joanna.Yan
 *
 */
public class WordUtil {

    public static CustomXWPFDocument generateWord(Map<String, Object> param, String template){
        CustomXWPFDocument doc=null;
        try {
            OPCPackage pack= POIXMLDocument.openPackage(template);
            doc=new CustomXWPFDocument(pack);
            if(param!=null&&param.size()>0){
                //处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc);
                //处理表格
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while(it.hasNext()){
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable =  cell.getParagraphs();
                            processParagraphs(paragraphListTable, param, doc);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 处理段落
     * @param paragraphList
     * @param param
     * @param doc
     */
    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param, CustomXWPFDocument doc){
        if(paragraphList!=null&&paragraphList.size()>0){
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs=paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
//                    System.out.println(run.getText(0));
                    if(text!=null){
                        boolean isSetText=false;
                        for (Map.Entry<String, Object> entry : param.entrySet()) {
                            String key=entry.getKey();
                            if(text.indexOf(key)!=-1){
                                isSetText=true;
                                Object value=entry.getValue();
                                if(value instanceof String){//文本替换

                                    text=text.replace(key, value.toString());
                                }else if(value instanceof Map){//图片替换
                                    text=text.replace(key, "");
                                    Map pic=(Map) value;
                                    int width=Integer.parseInt(pic.get("width").toString());
                                    int height=Integer.parseInt(pic.get("height").toString());
                                    int picType=getPictureType(pic.get("type").toString());
                                    byte[] byteArray = (byte[]) pic.get("content");
                                    ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                                    try {
                                        int ind = doc.addPicture(byteInputStream,picType);
                                        doc.createPicture(ind, width , height,paragraph);
                                    } catch (InvalidFormatException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        if(isSetText){
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据图片类型获取对应的图片类型代码
     * @param picType
     * @return
     */
    public static int getPictureType(String picType){
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if(picType!=null){
            if(picType.equalsIgnoreCase("png")){
                res=CustomXWPFDocument.PICTURE_TYPE_PNG;
            }else if(picType.equalsIgnoreCase("dib")){
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            }else if(picType.equalsIgnoreCase("emf")){
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            }else if(picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")){
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            }else if(picType.equalsIgnoreCase("wmf")){
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }
}

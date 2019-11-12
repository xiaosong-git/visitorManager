package com.manage.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.manage.model.VisitorRecord;

public class ExportExcel {

    public String export(List<VisitorRecord> clsList) {
        long millis = System.currentTimeMillis();
        String baseUrl="E://qrcodeimgs/";
        String excelName=String.valueOf(millis)+".xlsx";
        try(OutputStream out=new FileOutputStream(baseUrl+excelName)) {
            ExcelWriter writer=new ExcelWriter(out,ExcelTypeEnum.XLSX);
            if(!clsList.isEmpty()) {
                Sheet sheet=new Sheet(1,0,clsList.get(0).getClass());
                sheet.setSheetName("访客记录导出");
                writer.write(clsList, sheet);
            }
            writer.finish();
            return excelName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}


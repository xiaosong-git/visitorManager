package com.manage.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtil {

	/**

	 * 单个文件上传

	 * @param is

	 * @param fileName

	 * @param filePath

	 */

    public static String upFile(InputStream is,String fileName,String filePath){

		FileOutputStream fos = null;
	
		BufferedOutputStream bos = null;
	
		BufferedInputStream bis = null;
	
		File file = new File(filePath);
	
		if(!file.exists()){
			file.mkdirs();
		}
	
		String name = filePath+"//"+fileName;
		System.out.println("name:"+name);
		File f = new File(name);
		try {
			bis = new BufferedInputStream(is);
		
			fos = new FileOutputStream(f);
		
			bos = new BufferedOutputStream(fos);
		
			byte[] bt = new byte[4096];
		
			int len;
		
			while((len = bis.read(bt))>0){
				bos.write(bt, 0, len);
			}
	        return name;
		} catch (Exception e) {
	
			e.printStackTrace();
	        return null;
		}finally {
	
			try {
		
				if(null != bos){
			
					bos.close();
				
					bos = null;
				}
			
				if(null != fos){
			
					fos.close();
				
					fos= null;
			
				}
			
				if(null != is){
			
					is.close();
			
					is=null;
			
				}
			
				if (null != bis) {
			
					bis.close();
				
					bis = null;
			
				}
		
			} catch (Exception e) {
		
				e.printStackTrace();
		
			}
	
		}

	}
}

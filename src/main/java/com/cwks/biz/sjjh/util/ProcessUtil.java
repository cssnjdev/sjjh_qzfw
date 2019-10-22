package com.cwks.biz.sjjh.util;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class ProcessUtil {
	//传入进程名称processName

	public static boolean findProcess(String processName) {
	        BufferedReader bufferedReader = null;
	        try {
	        	String cmdStr="tasklist"+" -fi " + '"' + "USERNAME ne NT AUTHORITY\\SYSTEM " +'"'+" -fi " + '"' + "STATUS eq running " +'"'+" -fi " + '"' + "imagename eq " + processName +'"';
	        	Process proc = Runtime.getRuntime().exec(cmdStr);
	        	bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream(),"UTF-8"));
	            String line = null;
	            while ((line = bufferedReader.readLine()) != null) {
	                if (line.contains(processName)) {
	                    return true;
	                }
	            }
	            return false;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return false;
	        } finally {
	            if (bufferedReader != null) {
	                try {
	                    bufferedReader.close();
	                } catch (Exception ex) {
						ex.printStackTrace();
					}
	            }
	        }
	    }
	
	/**
	 * 打开IE浏览器访问页面
	 */
	public static void openIEBrowser(String url){
		 //启用cmd运行IE的方式来打开网址。
        String str = "cmd /c start iexplore "+url;
        try {
            Runtime.getRuntime().exec(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void shutIEBrowser(){
		String str = "taskkill /f /im iexplore.exe";
		try {
			Runtime.getRuntime().exec(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 打开默认浏览器访问页面
	 */
	public static void openDefaultBrowser(){
		//启用系统默认浏览器来打开网址。
        try {
            URI uri = new URI("http://blog.csdn.net/l1028386804");
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
 
    public static void main(String[] args) {
    	//openIEBrowser("http://yscdddl.gxds.tax.cn:9007?gdsbj=1^&wfxwbh=24501022017000000069");
    	//openDefaultBrowser();
    	//System.out.println(findProcess("iexplore.exe"));
    	
    }
}

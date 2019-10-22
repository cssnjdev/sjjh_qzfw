package com.cwks.bizcore.sjjh.core.utils;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.cwks.common.log.LogWritter;

/**
 * 基于FTP4J组件的FTP操作客户端帮助类
 * <p>
 * Title: FtpUtil.java
 * </p>
 * <p>
 * Description: Ftp文件帮助类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * </p>
 * <p>
 * Company: cssnj
 * </p>
 * 
 * @author hur
 * @version 1.0
 */
public final class FTPToolkit { 
	//单例模型
    private FTPToolkit() { } 

    /** 
     * 创建FTP连接 
     * @param host     主机名或IP 
     * @param port     ftp端口 
     * @param username ftp用户名 
     * @param password ftp密码 
     * @return 一个单例客户端 
     */ 
    public static FTPClient ftpConn(String host, int port, String username, String password) {
        FTPClient client = new FTPClient();
        try { 
            client.connect(host, port); 
            client.login(username, password);
            client.setCharset("GBK");
            //client.setType(FTPClient.TYPE_BINARY); 
            client.setType(FTPClient.TYPE_BINARY);
            client.setPassive(false);
        } catch (Exception e) { 
        	System.out.println("###[Error] FTPToolkit.ftpConn() "+e.getMessage());
        } 
        return client; 
    } 
    
    /** 
     * 创建FTP连接 被动模式
     * @param host     主机名或IP 
     * @param port     ftp端口 
     * @param username ftp用户名 
     * @param password ftp密码 
     * @return 一个单例客户端 
     */ 
    public static FTPClient ftpPassConn(String host, int port, String username, String password) {
        FTPClient client = new FTPClient();
        try { 
            client.connect(host, port); 
            client.login(username, password);
            client.setCharset("GBK");
            //client.setType(FTPClient.TYPE_BINARY); 
            client.setType(FTPClient.TYPE_BINARY);
            client.setPassive(true);
        } catch (Exception e) { 
        	System.out.println("###[Error] FTPToolkit.ftpConn() "+e.getMessage());
        } 
        return client; 
    } 
    
    /** 
     * 创建FTP连接 主动模式
     * @param host     主机名或IP 
     * @param port     ftp端口 
     * @param username ftp用户名 
     * @param password ftp密码 
     * @return 一个单例客户端 
     */ 
    public static FTPClient ftpActConn(String host, int port, String username, String password) {
        FTPClient client = new FTPClient();
        try { 
            client.connect(host, port); 
            client.login(username, password);
            client.setCharset("GBK");
            //client.setType(FTPClient.TYPE_BINARY); 
            client.setType(FTPClient.TYPE_BINARY);
            client.setPassive(false);
        } catch (Exception e) { 
        	System.out.println("###[Error] FTPToolkit.ftpConn() "+e.getMessage());
        } 
        return client; 
    } 
    
    /**
     * 取得目录下所有文件列表
     * @param path
     * @return
     */
    public static List<FTPFile> getlistFiles(FTPClient client, String path) {
        List<FTPFile> filesList = new ArrayList<FTPFile>();
        try {
        	client.changeDirectory(path);
        	FTPFile[] fileNames = client.list(); //.listNames();
            if (null != fileNames) {
                for (FTPFile file : fileNames) {
                	if(file.getType() == FTPFile.TYPE_FILE){
                		filesList.add(file);
                        //System.out.println(file.getName()+" | "+file.getSize()+" | "+DateUtil.converDateToString(file.getModifiedDate(),DateUtil.defaultDatePattern));
                	}
                }
            }
        } catch (Exception e) {
        	System.out.println("###[Error] FTPToolkit.getlistFiles()"+ e.getMessage());
        }
        return filesList;
    }
    /**
     * 取得目录下所有文件列表
     * @param path
     * @return
     */
    public static List<String> getlistFileNames(FTPClient client, String path) {
        List<String> filesList = new ArrayList<String>();
        try {
        	client.changeDirectory(path);
        	String[] fileNames = client.listNames();
            if (null != fileNames) {
                for (String file : fileNames) {
                	filesList.add(path+"/"+file);
                    //System.out.println(path+"/"+file);
                }
            }
        } catch (Exception e) {
        	System.out.println("###[Error] FTPToolkit.getlistFileNames()"+ e.getMessage());
        }
        return filesList;
    }    
    
    /**
     * 根据文件路径获取文件流
     * @param ftpFilePath
     * @param ftpPath
     * @return
    */
    public static InputStream fetchInputStream(FTPClient client, String ftpFilePath, String ftpPath) {
        InputStream is = null;
        try {
        	String localFilePath = getFileName("ftptmp/"+ftpFilePath) + "_ftp.tmp";
            File tempLocalFile = new File(localFilePath);
            boolean isExists = false;
            if (!tempLocalFile.exists()) {
                isExists = tempLocalFile.createNewFile();
            }else{
                isExists = true;
            }
            if(isExists){
                client.download(ftpPath, tempLocalFile);
                is = new FileInputStream(tempLocalFile);
            }
            //删除临时文件(由于不能关闭文件流，删除临时文件无效,所以最好使用deleteOnExit，外层程序使用文件流结束后，关闭流，自动删除)
            //tempLocalFile.deleteOnExit();
        } catch (Exception e) {
            System.out.println("###[Error] FTPToolkit.fetchInputStream()"+e.getMessage());
        }
        return is;
    }
   
    /**
    * 上传文件到FTP
    * @param ftpFilePath 要上传到FTP的路径
    * @param ftpFile 源数据
    */
   public static void uploadInputStream(FTPClient client, String ftpFilePath, InputStream ftpFile) {
       try {
    	   mkDirs(client,ftpFilePath.substring(0, ftpFilePath.lastIndexOf("/")));
    	   
    	   client.upload(ftpFilePath, ftpFile, 0L, 0L, null);
       } catch (Exception e) {
    	   System.out.println("###[Error] FTPToolkit.uploadInputStream()"+e.getMessage());
       }finally{
    	   try {
    		   //关闭文件流
    		   ftpFile.close();
			} catch (Exception e) {}
       }
   }

   
    /** 
     * FTP下载文件到本地一个文件夹,如果本地文件夹不存在，则创建必要的目录结构 
     * @param client          FTP客户端 
     * @param remoteFileName  FTP文件 
     * @param localFolderPath 存的本地目录 
     */ 
    public static void download(FTPClient client, String remoteFileName, String localFolderPath) {
        try {
        	int x = isExist(client, remoteFileName);
			MyFtpListener listener = MyFtpListener.instance("download"); 
			File localFolder = new File(localFolderPath);
            boolean isok= true;
            if (!localFolder.exists()) {
                isok= localFolder.mkdirs();
            }
            if(isok){
                if (x == FTPFile.TYPE_FILE) {
                    String localfilepath = formatPath4File(localFolderPath+File.separator+new File(remoteFileName).getName());
//			    if (listener != null) {
                    client.download(remoteFileName, new File(localfilepath), listener);
//		        }else{
//		            client.download(remoteFileName, new File(localfilepath));
//		        }
                }
            }
		} catch (Exception e) {
			LogWritter.sysError("###[Error] FTPToolkit.download():",e);
		} 
    } 

    
    //递归创建层级目录（坑爹的问题，原本想着递归处理，后来才发现如此的简单）
    private static void mkDirs(FTPClient client, String p) throws Exception {
	    if (null == p) { return;}

        if(!"".equals(p) && !"/".equals(p)){
        	String ps = "";
        	for(int i=0;i<p.split("/").length;i++){
        		ps += p.split("/")[i]+"/";
        		if (!isDirExist(client,ps)) {
        			client.createDirectory(ps);// 创建目录
        			client.changeDirectory(ps);// 进入创建的目录
        			System.out.println(">>>>> create directory:["+i+"]["+ps+"]");
    	    	}else{
    	    		//System.out.println("select directory:["+i+"]["+ps+"]");
    	    	}
        	}
        }
    }
    //检查目录是否存在
    private static boolean isDirExist(FTPClient client, String dir) {
    	try {
    		client.changeDirectory(dir);
    	} catch (Exception e) {
    		return false;
    	}
    	return true;
    }

    /** 
     * FTP上传本地文件到FTP的一个目录下 
     * @param client           FTP客户端 
     * @param localfilepath    本地文件路径 
     * @param remoteFolderPath FTP上传目录 
     */ 
    public static void upload(FTPClient client, String localfilepath, String remoteFolderPath) {
        try {
        	mkDirs(client,remoteFolderPath);
        	
			File localfile = new File(localfilepath); 
			upload(client, localfile, remoteFolderPath);
		} catch (Exception e) {
			System.out.println("###[Error] FTPToolkit.upload()"+e.getMessage());
		} 
    }    
    /** 
     * FTP上传本地文件到FTP的一个目录下 
     * @param client           FTP客户端 
     * @param localfile        本地文件 
     * @param remoteFolderPath FTP上传目录 
     */ 
    public static void upload(FTPClient client, File localfile, String remoteFolderPath) {
        remoteFolderPath = formatPath4FTP(remoteFolderPath); 
        MyFtpListener listener = MyFtpListener.instance("upload"); 
        try { 
            client.changeDirectory(remoteFolderPath); 
//            if (listener != null) {
                client.upload(localfile, listener); 
//            }else{
//                client.upload(localfile);
//            }
            client.changeDirectory("/"); 
        } catch (Exception e) { 
        	System.out.println("###[Error] FTPToolkit.upload()"+e.getMessage());
        } 
    } 
 
    /** 
     * 批量上传本地文件到FTP指定目录上 
     * @param client           FTP客户端 
     * @param localFilePaths   本地文件路径列表 
     * @param remoteFolderPath FTP上传目录 
     */ 
    public static void uploadListPath(FTPClient client, List<String> localFilePaths, String remoteFolderPath) {
        try { 
        	remoteFolderPath = formatPath4FTP(remoteFolderPath); 
            client.changeDirectory(remoteFolderPath); 
            MyFtpListener listener = MyFtpListener.instance("uploadListPath"); 
            for (String path : localFilePaths) { 
                File file = new File(path);
//                if (listener != null) {
                    client.upload(file, listener); 
//                }else {
//                    client.upload(file);
//                }
            } 
            client.changeDirectory("/"); 
        } catch (Exception e) { 
        	System.out.println("###[Error] FTPToolkit.uploadListPath()"+e.getMessage());
        } 
    } 
    /** 
     * 批量上传本地文件到FTP指定目录上 
     * @param client           FTP客户端 
     * @param localFiles       本地文件列表 
     * @param remoteFolderPath FTP上传目录 
     */ 
    public static void uploadListFile(FTPClient client, List<File> localFiles, String remoteFolderPath) {
        try { 
            client.changeDirectory(remoteFolderPath); 
            //remoteFolderPath = formatPath4FTP(remoteFolderPath);
            MyFtpListener listener = MyFtpListener.instance("uploadListFile"); 
            for (File file : localFiles) { 
//                if (listener != null){
                    client.upload(file, listener); 
//                }else{
//                    client.upload(file);
//                }
            } 
            client.changeDirectory("/"); 
        } catch (Exception e) {
        	LogWritter.sysError("###[Error] FTPToolkit.uploadListFile()：", e);
        } 
    } 
    


    /** 
     * 判断一个FTP路径是否存在，如果存在返回类型(FTPFile.TYPE_DIRECTORY=1、FTPFile.TYPE_FILE=0、FTPFile.TYPE_LINK=2)
     * @param client     FTP客户端 
     * @param remotePath FTP文件或文件夹路径 
     * @return 存在时候返回类型值(文件0，文件夹1，连接2)，不存在则返回-1 
     */ 
    public static int isExist(FTPClient client, String remotePath) {
    	int x = -1; 
    	try {
			remotePath = formatPath4FTP(remotePath); 
			
			FTPFile[] list = client.list(remotePath);
			if (list.length > 1){ 
				x = FTPFile.TYPE_DIRECTORY;
			}else if (list.length == 1) { 
			    FTPFile f = list[0];
			    if (f.getType() == FTPFile.TYPE_DIRECTORY) {
			    	x = FTPFile.TYPE_DIRECTORY;
			    }
			    //假设推理判断 
			    String _path = remotePath + "/" + f.getName(); 
			    if(client.list(_path).length == 1){
			    	x = FTPFile.TYPE_DIRECTORY;
			    }else{
			    	x = FTPFile.TYPE_FILE;
			    }
			} else { 
				client.changeDirectory(remotePath.substring(0, remotePath.lastIndexOf("/")+1)); 
				x = FTPFile.TYPE_FILE;
			}
		} catch (Exception e) {
			x = -1;
			LogWritter.sysError("###[Error] FTPToolkit.isExist()：", e);
		}
    	return x;
    } 

    /** 
     * 关闭FTP连接，关闭时候像服务器发送一条关闭命令 
     * @param client FTP客户端 
     * @return 关闭成功，或者链接已断开，或者链接为null时候返回true，通过两次关闭都失败时候返回false 
     */ 
    public static boolean closeConn(FTPClient client) {
        if (client == null) return true; 
        if (client.isConnected()) { 
            try { 
                client.disconnect(true); 
                return true; 
            } catch (Exception e) { 
            	LogWritter.sysError("###[Error] FTPToolkit.closeConn()：", e);
                try { 
                    client.disconnect(false); 
                } catch (Exception e1) {
                	LogWritter.sysError("###[Error] FTPToolkit.closeConn()：", e1);
                } 
            } 
        } 
        return true; 
    } 
    
    
    
    //###---------------------------------------------------------------------
    /** 
     * 格式化文件路径，将其中不规范的分隔转换为标准的分隔符,并且去掉末尾的文件路径分隔符。 
     * 本方法操作系统自适应 
     * @param path 文件路径 
     * @return 格式化后的文件路径 
     */ 
    public static String formatPath4File(String path) { 
        String reg0 = "\\\\+"; 
        String reg = "\\\\+|/+"; 
        String temp = path.trim().replaceAll(reg0, "/"); 
        temp = temp.replaceAll(reg, "/"); 
        if (temp.length() > 1 && temp.endsWith("/")) { 
            temp = temp.substring(0, temp.length() - 1); 
        } 
        temp = temp.replace('/', File.separatorChar); 
        return temp; 
    } 
    /** 
     * 格式化文件路径，将其中不规范的分隔转换为标准的分隔符 
     * 并且去掉末尾的"/"符号(适用于FTP远程文件路径或者Web资源的相对路径)。 
     * @param path 文件路径 
     * @return 格式化后的文件路径 
     */ 
    public static String formatPath4FTP(String path) { 
        String reg0 = "\\\\+"; 
        String reg = "\\\\+|/+"; 
        String temp = path.trim().replaceAll(reg0, "/"); 
        temp = temp.replaceAll(reg, "/"); 
        if (temp.length() > 1 && temp.endsWith("/")) { 
            temp = temp.substring(0, temp.length() - 1); 
        } 
        return temp; 
    } 
    /** 
     * 获取FTP路径的父路径，但不对路径有效性做检查 
     * @param path FTP路径 
     * @return 父路径，如果没有父路径，则返回null 
    */ 
    public static String genParentPath4FTP(String path) { 
        String f = new File(path).getParent(); 
        if (f == null) {
        	return null; 
        }else {
        	return formatPath4FTP(f); 
        }
    } 
    
    //###---------------------------------------------------------------------
 
    //获取指定目录下的文件
    public static File[] getPathFiles(String path){
    	File file = new File(path);  
        if (file.isDirectory()) {  
            File[] files = file.listFiles();  
            return files;
        }  
        return null;
    }
    
    //删除指定目录下的临时文件
    public static void deleteFiles(String path){
    	File file = new File(path);  
        if (file.isDirectory()) {  
            File[] files = file.listFiles();
            if(files != null){
                for (int i = 0; i < files.length; i++) {
                    //String name = files[i].getName();
                    //if(name.trim().toLowerCase().endsWith("_ftp.tmp")) {
                    //System.out.println(name + "\t");
                    //}
                    //清空临时文件 _ftp.tmp
                    if(!files[i].delete()){
                        files[i].deleteOnExit();
                    }
                }
            }
        }  
    }
    
    //截取文件名称
    public static String getFileName(String fileName){
    	String fs = "F"+System.currentTimeMillis();
    	try {
			if(fileName != null && !"".equals(fileName)){
				if(fileName.lastIndexOf("/") > 0 && fileName.lastIndexOf(".") > 0){
					fs = fileName.substring(fileName.lastIndexOf("/")+1);
				}else{
					fs = fileName;
				}
				
				if(fs.length() >50){
					fs = fs.substring(0,50);
				}
				return fs;
			}else{
				return fs;
			}
		} catch (Exception e) {
			LogWritter.sysError("###[Error] FTPToolkit.getFileName()：", e);
		}
    	return fs;
    }
    
    
    
    public static void main(String args[]) throws Exception { 
//        FTPClient f41 = FTPToolkit.ftpConn("129.2.8.31", 21, "root", "root31");
//        FTPClient f42 = FTPToolkit.ftpConn("129.2.8.32", 21, "root", "root32");
      
        
        //InputStream in = FTPToolkit.fetchInputStream(f41,"/home/photofile/wenzi-003.jpg", null);
        //FTPToolkit.uploadInputStream(f41,"/home/photofile/aa.jpg",in);
        //FTPToolkit.upload(f41, "E:\\demo\\av1.jpg", "/home/photofile/10/20"); 
        //FTPToolkit.download(f41, "/home/photofile/wenzi-100.jpg", "E:\\"); 
        
        //FTPToolkit.getlistFiles(f41,"/home/photofile");
        
        /*File tempLocalFile = new File("F:/jslteardomain.zip.fiq");
        FTPClient root = FTPToolkit.ftpActConn("192.168.1.138", 21, "flzlwww", "Dzswj@1234");
        InputStream fis = new FileInputStream(tempLocalFile);
        FTPToolkit.uploadInputStream(root,"/jslteardomain.zip.fiq",fis);
        FTPToolkit.closeConn(root);*/
        //文件流对流读取操作
        /*List<String> ls = null;//FTPToolkit.getlistFileNames(f41,"/home/photofile");
        if(ls != null && ls.size() >0){
        	InputStream in=null;
			for(int i =0;i<ls.size();i++){
				//in = FTPToolkit.fetchInputStream(f41,ls.get(i));
				if(in != null && in.available() > 0){
				  	FTPToolkit.uploadInputStream(f42,"/home/wasadmin/photoSysTempFTP/"+getFileName(ls.get(i)),in);
				  	f41.deleteFile(ls.get(i));
				}
			}
			
			//清空项目下的临时文件
			FTPToolkit.deleteFiles("ftptmp");
        }
     
        FTPToolkit.closeConn(f41); 
        FTPToolkit.closeConn(f42); */
    }
}


/** 
* FTP监听器
*/ 
class MyFtpListener implements FTPDataTransferListener {
    private String tag;
    private MyFtpListener(String tags) { 
            this.tag = tags; 
    } 
    
    public static MyFtpListener instance(String tags) { 
        return new MyFtpListener(tags); 
    } 
    @Override
    public void started() { 
        LogWritter.sysDebug(tag+ "：FTP启动。。。。。。");
    } 
    @Override
    public void transferred(int length) {
        LogWritter.sysDebug(tag+ "：FTP传输["+length+"]。。。。。。");
    } 
    @Override
    public void completed() {
        LogWritter.sysDebug(tag+ "：FTP完成。。。。。。");
    } 
    @Override
    public void aborted() {
        LogWritter.sysDebug(tag+ "：FTP中止。。。。。。");
    } 
    @Override
    public void failed() {
        LogWritter.sysDebug(tag+ "：FTP挂掉。。。。。。");
    } 
}
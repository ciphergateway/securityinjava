/*
 * 系统名称:QuickBundle --> cncpur
 * 
 * 文件名称: org.quickbundle.tools.support.encrypt --> RmZipHelper.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2007-12-7 下午01:55:13 创建1.0.0版 (baixiaoyong)
 * 
 */
package org.quickbundle.tools.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmZipHelper {
	public final static String ZIP_ENCODING_GBK = "GBK";
	public final static String ZIP_ENCODING_UTF8 = "UTF-8";
	
    public final static int MAX_ZIP_SIZE = 10 * 1024 * 1024;
    public final static int MAX_TEMPLATE_SIZE = 200 * 1024;
    private final static int BUFFER_SIZE = 16384;
    
    public static File createZipFile(File[] files, File target, String encoding) {
    	try {
    		target.getParentFile().mkdirs();
    		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(target), Charset.forName(encoding));
    		for (int i = 0; i < files.length; i++) {
    			fileZip(zos, files[i], "");
    		}
    		zos.close();
    	} catch (Exception e) {
    		throw new RuntimeException("Failed to process ZIP file.", e);
    	}
    	return target;
    }
    
    //实现zip文件加入ZipOutputStream
    private static void fileZip(ZipOutputStream zos, File f, String base) throws Exception {
        // 如果传入的是目录
        if (f.isDirectory()) {
            File[] fl = f.listFiles();

            if (base == null) {
                base = "";
            } else if (base.length() == 0) {
                base = f.getName() + "/";
            } else {
                base = base + f.getName() + "/";
            }
            for (int i = 0; i < fl.length; i++) {
                fileZip(zos, fl[i], base);
            }
        } else if (f.isFile()) {
            zos.putNextEntry(new ZipEntry(base + f.getName()));
            FileInputStream in = new FileInputStream(f);
            byte[] bb = new byte[2048];
            int aa = 0;
            while ((aa = in.read(bb)) != -1) {
                zos.write(bb, 0, aa);
            }
            in.close();
        }
    }
 
    
    /**
     * 解压缩文件zipFile保存在directory目录下
     * 
     * @param directory
     * @param zipFile
     */
    public static void unZip(String zipFile, String directory, String encoding) {
    	try {
			extractFile(new ZipFile(new File(zipFile), ZipFile.OPEN_READ, Charset.forName(encoding)), directory);
		} catch (IOException e) {
			throw new RuntimeException("Failed to process ZIP file.", e);
		}
    }
    
	/**
	 * Extract the file and folder structure of a ZIP file into the specified directory
	 * 
	 * @param archive       The ZIP archive to extract
	 * @param extractDir    The directory to extract into
	 */
	public static void extractFile(ZipFile archive, String extractDir)
	{
	    String fileName;
	    String destFileName;
	    byte[] buffer = new byte[BUFFER_SIZE];
	    extractDir = extractDir + File.separator;
	    try
	    {
	        for (Enumeration e = archive.entries(); e.hasMoreElements();)
	        {
	            ZipEntry entry = (ZipEntry) e.nextElement();
	            if (!entry.isDirectory())
	            {
	                fileName = entry.getName();
	                fileName = fileName.replace('/', File.separatorChar);
	                destFileName = extractDir + fileName;
	                File destFile = new File(destFileName);
	                String parent = destFile.getParent();
	                if (parent != null)
	                {
	                    File parentFile = new File(parent);
	                    if (!parentFile.exists()) parentFile.mkdirs();
	                }
	                InputStream in = new BufferedInputStream(archive.getInputStream(entry), BUFFER_SIZE);
	                OutputStream out = new BufferedOutputStream(new FileOutputStream(destFileName), BUFFER_SIZE);
	                int count;
	                while ((count = in.read(buffer)) != -1)
	                {
	                    out.write(buffer, 0, count);
	                }
	                in.close();
	                out.close();
	            }
	            else
	            {
	                File newdir = new File(extractDir + entry.getName());
	                newdir.mkdirs();
	            }
	        }
	    }
	    catch (Exception e)
	    {
	        throw new RuntimeException("Failed to process ZIP file.", e);
	    }
	}

    /**
     * 功能: 从上传的文件中获取xls
     *
     * @param zipFile
     * @return
     */
    public static File getExcelFromFile(File zipFile) {
        if(!zipFile.exists()) {
            return null;
        } else if(zipFile.toString().endsWith(".xls")) {
            return zipFile;
        } else if(zipFile.toString().endsWith(".zip")){
            //解压缩
            File unzipFolder = new File(System.getProperty("java.io.tmpdir") + File.separator + "rm" +  File.separator +  zipFile.getName() + "_unzip");
            RmZipHelper.unZip(zipFile.toString(), unzipFolder.toString(), ZIP_ENCODING_UTF8);
            File[] aUnzipFile = unzipFolder.listFiles();
            for (int i = 0; i < aUnzipFile.length; i++) {
                if(aUnzipFile[i].toString().endsWith(".xls") && aUnzipFile[i].isFile()) {
                    return aUnzipFile[i];
                }
            }
            return null;
        } else {
            throw new RuntimeException("文件不是.xls或.zip格式");
        }
    }

}

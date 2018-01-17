package gbkTest;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author SMF
 * @time 2018年1月17日
 */
public class GBK2UTF8 {

	public static void main(String[] args) throws Exception {

		String gbkDirPath = "D:\\svnAndroid\\megrezMobile\\MegrezLib\\src";// GBK编码格式源码文件路径
		String utf8DirPath = "D:\\UTF8\\src";// 转为UTF-8编码格式源码文件保存路径

		Collection<File> gbkFileList = FileUtils.listFiles(new File(gbkDirPath), new String[] { "java" }, true);// 获取所有java文件
		for (File gbkFile : gbkFileList) {
			String utf8FilePath = utf8DirPath + gbkFile.getAbsolutePath().substring(gbkDirPath.length());// UTF-8编码格式文件保存路径
			FileUtils.writeLines(new File(utf8FilePath), "UTF-8", FileUtils.readLines(gbkFile, "GBK"));// 使用GBK编码格式读取文件，然后用UTF-8编码格式写入数据
		}
	}

}

package org.quickbundle.tools.helper;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class RmZipHelperTest {
	@Rule
	public TestRule watcher = new TestWatcher() {
	   protected void starting(Description description) {
	      System.out.println("--------" + description.getDisplayName() + "--------");
	   }
	};
	
	String tmpDir = System.getProperty("java.io.tmpdir");
	String cgTestDir = System.getProperty("user.home") + File.separator + ".ciphergateway";
	File userHomeZip = new File(cgTestDir + File.separator + "abc.zip");

	@Before
	public void init() throws Exception {
		RmFileHelper.saveFile("aaaaaa", tmpDir + File.separator + "a.txt");
		RmFileHelper.saveFile("bbbbbb", tmpDir + File.separator + "b.txt");
		RmFileHelper.saveFile("中文", tmpDir + File.separator + "中文.txt");
	}

	@Test
	public void createZipFile() throws Exception {
		File targetZip = new File(tmpDir + File.separator + "abc.zip");
		RmZipHelper.createZipFile(new File[] {
				new File(tmpDir + File.separator + "a.txt"),
				new File(tmpDir + File.separator + "b.txt"),
				new File(tmpDir + File.separator + "中文.txt")
		}, targetZip, RmZipHelper.ZIP_ENCODING_UTF8);
		assertTrue(targetZip.exists());
		
		RmFileHelper.copyFile(targetZip, userHomeZip);
		assertTrue(userHomeZip.exists());
		targetZip.delete();
		assertTrue(!targetZip.exists());
	}
	
	@Test
	public void unZip() throws Exception {
		createZipFile();
		assertTrue(userHomeZip.exists());
		RmZipHelper.unZip(userHomeZip.toString(), cgTestDir, RmZipHelper.ZIP_ENCODING_UTF8);
		assertTrue(new File(cgTestDir + File.separator + "中文.txt").exists());
		new File(cgTestDir + File.separator + "abc.zip" ).delete();
		new File(cgTestDir + File.separator + "a.txt" ).delete();
		new File(cgTestDir + File.separator + "b.txt" ).delete();
		new File(cgTestDir + File.separator + "中文.txt" ).delete();
		assertTrue(!new File(cgTestDir + File.separator + "中文.txt").exists());
	}
	
}

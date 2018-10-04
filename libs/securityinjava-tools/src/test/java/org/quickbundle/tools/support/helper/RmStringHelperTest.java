package org.quickbundle.tools.support.helper;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class RmStringHelperTest {

	@Rule
	public TestRule watcher = new TestWatcher() {
	   protected void starting(Description description) {
	      System.out.println("--------" + description.getDisplayName() + "--------");
	   }
	};
	
	@Test
    public void testSubstring() throws Exception {
	    String str = "abcde";
	    
	    System.out.println("^" + str.substring(str.length()) + "$");
	}
    
}

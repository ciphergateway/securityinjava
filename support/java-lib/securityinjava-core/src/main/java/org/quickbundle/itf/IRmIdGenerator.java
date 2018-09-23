package org.quickbundle.itf;

public interface IRmIdGenerator {
	public void init();
	public String[] requestIdInner(String tableName, int length);
}

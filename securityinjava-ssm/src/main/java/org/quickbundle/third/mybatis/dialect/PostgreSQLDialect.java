package org.quickbundle.third.mybatis.dialect;


public class PostgreSQLDialect implements Dialect{
	public String getLimitString(String sql, int offset, int limit) {
		StringBuilder result = new StringBuilder(sql);
		result.append(" limit ").append(limit).append(" offset ").append(offset);
		return result.toString();
	}

}
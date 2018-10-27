package org.quickbundle.config;

import java.io.File;

public class RmConfigVo extends RmBaseConfigVo {
	
	private RmBaseConfigVo singleton = null;
	
	RmConfigVo(RmBaseConfigVo singleton) {
		super();
		this.singleton = singleton;
	}
	
	/**
	 * @return the warHome
	 */
	public String getWarHome() {
		return singleton.getWarHome();
	}

	/**
	 * @param warHome the warHome to set
	 */
	public void setWarHome(String warHome) {
		singleton.setWarHome(warHome);
	}

	/**
	 * @return 是集群模式？或单机？
	 */
	public boolean isClusterMode() {
		return singleton.isClusterMode();
	}

	/**
	 * @param clusterMode the clusterMode to set
	 */
	public void setClusterMode(boolean clusterMode) {
		singleton.setClusterMode(clusterMode);
	}

	/**
	 * @return 获得默认数据源的数据库类型  IGlobalConstants.DATABASE_PRODUCT_NAME_...
	 */
	public String getDatabaseProductName() {
		return singleton.getDatabaseProductName();
	}

	/**
	 * @param databaseProductName the databaseProductName to set
	 */
	public void setDatabaseProductName(String databaseProductName) {
		singleton.setDatabaseProductName(databaseProductName);
	}

	/**
	 * @return 系统是否开发调试状态(系统综合运行性能较低，优化了应用启动速度。同时sql的?替换输出，日志记录了sql真实数据)
	 */
	public boolean isSystemDebugMode() {
		return singleton.isSystemDebugMode();
	}

	/**
	 * @param systemDebugMode the systemDebugMode to set
	 */
	public void setSystemDebugMode(boolean systemDebugMode) {
		singleton.setSystemDebugMode(systemDebugMode);
	}

	/**
	 * @return 本集群节点RmIdFactory产生的主键前缀
	 */
	public String getShardingPrefix() {
		return singleton.getShardingPrefix();
	}

	/**
	 * @param shardingPrefix the shardingPrefix to set
	 */
	public void setShardingPrefix(String shardingPrefix) {
		singleton.setShardingPrefix(shardingPrefix);
	}
	
	/**
	 * @return 系统用户是否唯一登录，同时登录会强制踢出第一个用户
	 */
	public boolean isUserUniqueLogin() {
		return singleton.isUserUniqueLogin();
	}

	/**
	 * @param userUniqueLogin the userUniqueLogin to set
	 */
	public void setUserUniqueLogin(boolean userUniqueLogin) {
		singleton.setUserUniqueLogin(userUniqueLogin);
	}

	/**
	 * @return 默认的分页条数，会被rm.xml/rm/RmJspHelper/pageSize覆盖
	 */
	public int getDefaultPageSize() {
		return singleton.getDefaultPageSize();
	}

	/**
	 * @param defaultPageSize the defaultPageSize to set
	 */
	public void setDefaultPageSize(int defaultPageSize) {
		singleton.setDefaultPageSize(defaultPageSize);
	}

	/**
	 * @return the defaultBatchSize
	 */
	public int getDefaultBatchSize() {
		return singleton.getDefaultBatchSize();
	}

	/**
	 * @param defaultBatchSize the defaultBatchSize to set
	 */
	public void setDefaultBatchSize(int defaultBatchSize) {
		singleton.setDefaultBatchSize(defaultBatchSize);
	}

	/**
	 * @return 得到系统简短描述
	 */
	public String getAppDescription() {
		return singleton.getAppDescription();
	}

	/**
	 * @param appDescription the appDescription to set
	 */
	public void setAppDescription(String appDescription) {
		singleton.setAppDescription(appDescription);
	}

	/**
	 * @return 是否记录request的执行时间和SQL数量
	 */
	public boolean isLogRequest() {
		return singleton.isLogRequest();
	}

	/**
	 * @param logRequest the logRequest to set
	 */
	public void setLogRequest(boolean logRequest) {
		singleton.setLogRequest(logRequest);
	}

	/**
	 * @return the createPythonLibIfNotExist
	 */
	public boolean isCreatePythonLibIfNotExist() {
		return singleton.isCreatePythonLibIfNotExist();
	}

	/**
	 * @param createPythonLibIfNotExist the createPythonLibIfNotExist to set
	 */
	public void setCreatePythonLibIfNotExist(boolean createPythonLibIfNotExist) {
		singleton.setCreatePythonLibIfNotExist(createPythonLibIfNotExist);
	}

	/**
	 * @return the logicDeleteFile
	 */
	public boolean isLogicDeleteFile() {
		return singleton.isLogicDeleteFile();
	}

	/**
	 * @param logicDeleteFile the logicDeleteFile to set
	 */
	public void setLogicDeleteFile(boolean logicDeleteFile) {
		singleton.setLogicDeleteFile(logicDeleteFile);
	}

	/**
	 * @return the recycleBinFolder
	 */
	public String getRecycleBinFolder() {
		return singleton.getRecycleBinFolder();
	}

	/**
	 * @param recycleBinFolder the recycleBinFolder to set
	 */
	public void setRecycleBinFolder(String recycleBinFolder) {
		singleton.setRecycleBinFolder(recycleBinFolder);
	}

	public boolean isRememberPage() {
		return singleton.isRememberPage();
	}

	public void setRememberPage(boolean rememberPage) {
		singleton.setRememberPage(rememberPage);
	}

	public String getDefaultFont() {
		return singleton.getDefaultFont();
	}

	public void setDefaultFont(String defaultFont) {
		singleton.setDefaultFont(defaultFont);
	}
	
	/**
	 * 默认编码
	 */
	public String getDefaultEncode() {
		return singleton.getDefaultEncode();
	}
	
    public void setDefaultEncode(String defaultEncode) {
        singleton.setDefaultEncode(defaultEncode);
    }
	
	/**
	 * 默认实数数值的精度
	 */
	public int getDefaultNumberScale() {
		return singleton.getDefaultNumberScale();
	}
	
   public void setDefaultNumberScale(int defaultNumberScale ) {
        singleton.setDefaultNumberScale(defaultNumberScale);
    }


	/**
	 * 是否启动任务调度
	 */
	private boolean schedulerStart = false;
	/**
	 * @return 是否启动任务调度
	 */
	public boolean isSchedulerStart() {
		return schedulerStart;
	}
	/**
	 * @param schedulerStart the schedulerStart to set
	 */
	public void setSchedulerStart(boolean schedulerStart) {
		this.schedulerStart = schedulerStart;
	}
	
	//lazy start scheduler time(second), 0 or -1 ignore this | 延时启动scheduler的秒数
	private long schedulerStartLazy;

	/**
	 * lazy start scheduler time(second), 0 or -1 ignore this | 延时启动scheduler的秒数
	 * @return
	 */
	public long getSchedulerStartLazy() {
		return schedulerStartLazy;
	}
	public void setSchedulerStartLazy(long schedulerStartLazy) {
		this.schedulerStartLazy = schedulerStartLazy;
	}
	
	   
    //是否开启登录图片校验
    private boolean loginValidateVerifyCode = false;
    
	/**
	 * 登录时是否有校验码
	 * 
	 * @return
	 */
	public boolean isLoginValidateVerifyCode() {
        return loginValidateVerifyCode;
    }

    public void setLoginValidateVerifyCode(boolean loginValidateVerifyCode) {
        this.loginValidateVerifyCode = loginValidateVerifyCode;
    }
    
    //节点心跳间隔，单位ms
    private long nodeHeartbeatInterval = 1000 * 60;
    
    /**
     * 节点心跳间隔，单位ms
     * 
     * @return
     */
    public long getNodeHeartbeatInterval() {
        return nodeHeartbeatInterval;
    }

    public void setNodeHeartbeatInterval(long nodeHeartbeatInterval) {
        this.nodeHeartbeatInterval = nodeHeartbeatInterval;
    }

    //系统缓存检查周期
    private long cacheCheckInterval = 1000 * 2;

    /**
     * 系统缓存检查周期
     * 
     * @return
     */
    public long getCacheCheckInterval() {
        return cacheCheckInterval;
    }

    public void setCacheCheckInterval(long cacheCheckInterval) {
        this.cacheCheckInterval = cacheCheckInterval;
    }

    //是否全局监控
    private boolean globalMonitor = true;
    
    /**
     * 是否全局监控
     * 
     * @return
     */
    public boolean isGlobalMonitor() {
        return globalMonitor;
    }

    public void setGlobalMonitor(boolean globalMonitor) {
        this.globalMonitor = globalMonitor;
    }

    //系统缓存刷新周期
    private long cacheFlushInterval = 1000 * 60 * 5;

    /**
     * 系统缓存刷新周期
     * 
     * @return
     */
    public long getCacheFlushInterval() {
        return cacheFlushInterval;
    }

    public void setCacheFlushInterval(long cacheFlushInterval) {
        this.cacheFlushInterval = cacheFlushInterval;
    }

    //批处理sql的最大记录日志数量
    private long maxLogSqlBatchSize = 100;
    
    /**
     * 批处理sql的最大记录日志数量
     * 
     * @return
     */
    public long getMaxLogSqlBatchSize() {
        return maxLogSqlBatchSize;
    }

    public void setMaxLogSqlBatchSize(long maxLogSqlBatchSize) {
        this.maxLogSqlBatchSize = maxLogSqlBatchSize;
    }

    //系统用户登录是否DEMO状态(不校验用户数据库)
    private boolean userDemoMode = true;
    
    /**
     * 系统用户登录是否DEMO状态(不校验用户数据库)
     * 
     * @return
     */
    public boolean isUserDemoMode() {
        return userDemoMode;
    }

    public void setUserDemoMode(boolean userDemoMode) {
        this.userDemoMode = userDemoMode;
    }

    //是否给insert和update的sql语句自动加ts
    private boolean sqlUpdateAutoAppendTs = false;
    
    /**
     * 是否给insert和update的sql语句自动加ts
     * 
     * @return
     */
    public boolean isSqlUpdateAutoAppendTs() {
        return sqlUpdateAutoAppendTs;
    }

    public void setSqlUpdateAutoAppendTs(boolean sqlUpdateAutoAppendTs) {
        this.sqlUpdateAutoAppendTs = sqlUpdateAutoAppendTs;
    }

    //默认的临时文件夹
    private File defaultTempDir = new File(System.getProperty("java.io.tmpdir") + File.separator + "securityinjava");
    
    /**
     * 默认的临时文件夹
     * 
     * @return
     */
    public File getDefaultTempDirWithMkdirs() {
        if(!defaultTempDir.exists()) {
            defaultTempDir.mkdirs();
        }
        return defaultTempDir;
    }

    public File getDefaultTempDir() {
        return defaultTempDir;
    }
    
    public void setDefaultTempDir(File defaultTempDir) {
        this.defaultTempDir = defaultTempDir;
    }

    //默认的树形编码起始值，适用于简单的纯数字树，每个节点下最多有900个子节点
    private String defaultTreeCodeFirst = "100";

    /**
     * 默认的树形编码起始值，适用于简单的纯数字树，每个节点下最多有900个子节点
     * 
     * @return
     */
    public String getDefaultTreeCodeFirst() {
        return defaultTreeCodeFirst;
    }

    public void setDefaultTreeCodeFirst(String defaultTreeCodeFirst) {
        this.defaultTreeCodeFirst = defaultTreeCodeFirst;
    }

    //指定最大循环次数，防止死循环
    private int maxCircleCount = 10000;
    
    /**
     * 指定最大循环次数，防止死循环
     * 
     * @return
     */
    public int getMaxCircleCount() {
        return maxCircleCount;
    }

    public void setMaxCircleCount(int maxCircleCount) {
        this.maxCircleCount = maxCircleCount;
    }

    //定义单实例全局缓存的最大容量，防止溢出攻击，如公开的url列表
    private int maxCacheSize = 10000;

    public int getMaxCacheSize() {
        return maxCacheSize;
    }

    /**
     * 定义单实例全局缓存的最大容量，防止溢出攻击，如公开的url列表
     * 
     * @param maxCacheSize
     */
    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }
    
}
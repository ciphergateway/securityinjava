package org.quickbundle.config;

/**
 * @author qb
 *
 */
public class RmBaseConfigVo {
	RmBaseConfigVo() {
		super();
	}
	
	private String warHome = System.getProperty("user.home") + "/.ciphergateway/default";
	
	/**
	 * 	<!-- cloud node info collected automatically? or read from rm.xml? -->
	 * @return 是集群模式？或单机？
	 */
	private boolean clusterMode = false;
    
    /**
     * 获得默认数据源的数据库类型，默认数据库类型是NULL
     * IGlobalConstants.DATABASE_PRODUCT_NAME_...
     */
	private String databaseProductName = null;
	
    /**
     * 系统是否开发调试状态(性能很低，便利于开发调试。同时sql的?替换输出，日志记录了sql真实数据)
     * 默认不是开发调试状态，是正式运行状态
     */
    private boolean systemDebugMode = true;
	
	/**
	 * primary key prefix of this cluster node, range: 1000-9222 
	        本集群节点RmIdFactory产生的主键前缀
	 */
	private String shardingPrefix = "1000";

	/**
	 * 系统用户是否唯一登录，同时登录会强制踢出第一个用户
	 */
	private boolean userUniqueLogin = true;

	
	/**
	 * 默认的分页条数，默认按15条分页，会被rm.xml/rm/RmJspHelper/pageSize覆盖
	 */
	private int defaultPageSize = 15;
	
	/**
	 * 默认的批处理条数，用于sql的union all条数
	 */
	private int defaultBatchSize = 10;
	
	/**
	 * 得到系统描述
	 */
	private String appDescription = "QuickBundle System";
	
	/**
	 * 是否记录request的执行时间和SQL数量
	 */
	private boolean logRequest = true;
	
	/**
	 * Python的Lib目录不存在时，自动解压缩
	 */
	private boolean createPythonLibIfNotExist = true;

	/**
	 * 是否逻辑删除文件
	 */
	private boolean logicDeleteFile = false;
	
	/**
	 * 逻辑删除的回收站文件夹
	 */
	private String recycleBinFolder = warHome + "/recycle_bin";
	
	/**
	 * 是否记住当前url列表的在第几行
	 */
	private boolean rememberPage = false;
	
	/**
	 * 默认字体
	 */
	private String defaultFont = null;
	
	/**
	 * 最多in多少条记录？
	 */
	private int maxSqlInCount = 1000;
	
	/**
	 * 系统默认编码
	 */
	private String defaultEncode = "UTF-8";
	
	/**
	 * 默认实数数值的精度
	 */
	private int defaultNumberScale = 2;

	/**
	 * @return the warHome
	 */
	public String getWarHome() {
		return warHome;
	}

	/**
	 * @param warHome the warHome to set
	 */
	void setWarHome(String warHome) {
		this.warHome = warHome;
	}

	/**
	 * @return 是集群模式？或单机？
	 */
	public boolean isClusterMode() {
		return clusterMode;
	}

	/**
	 * @param clusterMode the clusterMode to set
	 */
	void setClusterMode(boolean clusterMode) {
		this.clusterMode = clusterMode;
	}

	/**
	 * @return 获得默认数据源的数据库类型  IGlobalConstants.DATABASE_PRODUCT_NAME_...
	 */
	public String getDatabaseProductName() {
		return databaseProductName;
	}

	/**
	 * @param databaseProductName the databaseProductName to set
	 */
	void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}

	/**
	 * @return 系统是否开发调试状态(系统综合运行性能较低，优化了应用启动速度。同时sql的?替换输出，日志记录了sql真实数据)
	 */
	public boolean isSystemDebugMode() {
		return systemDebugMode;
	}

	/**
	 * @param systemDebugMode the systemDebugMode to set
	 */
	void setSystemDebugMode(boolean systemDebugMode) {
		this.systemDebugMode = systemDebugMode;
	}

	/**
	 * @return 本集群节点RmIdFactory产生的主键前缀
	 */
	public String getShardingPrefix() {
		return shardingPrefix;
	}
	/**
	 * @param shardingPrefix the shardingPrefix to set
	 */
	public void setShardingPrefix(String shardingPrefix) {
		this.shardingPrefix = shardingPrefix;
	}
	
	/**
	 * @return 系统用户是否唯一登录，同时登录会强制踢出第一个用户
	 */
	public boolean isUserUniqueLogin() {
		return userUniqueLogin;
	}

	/**
	 * @param userUniqueLogin the userUniqueLogin to set
	 */
	void setUserUniqueLogin(boolean userUniqueLogin) {
		this.userUniqueLogin = userUniqueLogin;
	}

	/**
	 * @return 默认的分页条数，会被rm.xml/rm/RmJspHelper/pageSize覆盖
	 */
	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	/**
	 * @param defaultPageSize the defaultPageSize to set
	 */
	void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	/**
	 * @return the defaultBatchSize
	 */
	public int getDefaultBatchSize() {
		return defaultBatchSize;
	}

	/**
	 * @param defaultBatchSize the defaultBatchSize to set
	 */
	void setDefaultBatchSize(int defaultBatchSize) {
		this.defaultBatchSize = defaultBatchSize;
	}

	/**
	 * @return 得到系统简短描述
	 */
	public String getAppDescription() {
		return appDescription;
	}

	/**
	 * @param appDescription the appDescription to set
	 */
	void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	/**
	 * @return 是否记录request的执行时间和SQL数量
	 */
	public boolean isLogRequest() {
		return logRequest;
	}

	/**
	 * @param logRequest the logRequest to set
	 */
	void setLogRequest(boolean logRequest) {
		this.logRequest = logRequest;
	}

	/**
	 * @return the createPythonLibIfNotExist
	 */
	public boolean isCreatePythonLibIfNotExist() {
		return createPythonLibIfNotExist;
	}

	/**
	 * @param createPythonLibIfNotExist the createPythonLibIfNotExist to set
	 */
	void setCreatePythonLibIfNotExist(boolean createPythonLibIfNotExist) {
		this.createPythonLibIfNotExist = createPythonLibIfNotExist;
	}

	/**
	 * @return the logicDeleteFile
	 */
	public boolean isLogicDeleteFile() {
		return logicDeleteFile;
	}

	/**
	 * @param logicDeleteFile the logicDeleteFile to set
	 */
	void setLogicDeleteFile(boolean logicDeleteFile) {
		this.logicDeleteFile = logicDeleteFile;
	}

	/**
	 * @return the recycleBinFolder
	 */
	public String getRecycleBinFolder() {
		return recycleBinFolder;
	}

	/**
	 * @param recycleBinFolder the recycleBinFolder to set
	 */
	void setRecycleBinFolder(String recycleBinFolder) {
		this.recycleBinFolder = recycleBinFolder;
	}

	public boolean isRememberPage() {
		return rememberPage;
	}

	void setRememberPage(boolean rememberPage) {
		this.rememberPage = rememberPage;
	}

	public String getDefaultFont() {
		return defaultFont;
	}

	void setDefaultFont(String defaultFont) {
		this.defaultFont = defaultFont;
	}
	
	public int getMaxSqlInCount() {
		return maxSqlInCount;
	}

	public void setMaxSqlInCount(int maxSqlInCount) {
		this.maxSqlInCount = maxSqlInCount;
	}

    public String getDefaultEncode() {
        return defaultEncode;
    }

    public void setDefaultEncode(String defaultEncode) {
        this.defaultEncode = defaultEncode;
    }

    public int getDefaultNumberScale() {
        return defaultNumberScale;
    }

    public void setDefaultNumberScale(int defaultNumberScale) {
        this.defaultNumberScale = defaultNumberScale;
    }

}
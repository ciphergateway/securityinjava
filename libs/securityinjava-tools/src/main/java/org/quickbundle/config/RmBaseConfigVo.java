package org.quickbundle.config;

/**
 * @author qb
 *
 */
public class RmBaseConfigVo {
	RmBaseConfigVo() {
		super();
	}
	
	//war包的Home目录
	private String warHome = System.getProperty("user.home") + "/.ciphergateway/default";

	 /**
	  * war包的Home目录
	  * 
	  * @return the warHome
     */
    public String getWarHome() {
        return warHome;
    }
    public void setWarHome(String warHome) {
        this.warHome = warHome;
    }
	
	/**
	 * 	<!-- cloud node info collected automatically? or read from rm.xml? -->
	 * @return 是集群模式？或单机？
	 */
	private boolean clusterMode = false;
	
    /**
     * @return 是集群模式？或单机？
     */
    public boolean isClusterMode() {
        return clusterMode;
    }
    public void setClusterMode(boolean clusterMode) {
        this.clusterMode = clusterMode;
    }
    
    /**
     * 获得默认数据源的数据库类型，默认数据库类型是NULL
     * IGlobalConstants.DATABASE_PRODUCT_NAME_...
     */
	private String databaseProductName = null;
	
    /**
     * @return 获得默认数据源的数据库类型  IGlobalConstants.DATABASE_PRODUCT_NAME_...
     */
    public String getDatabaseProductName() {
        return databaseProductName;
    }
    public void setDatabaseProductName(String databaseProductName) {
        this.databaseProductName = databaseProductName;
    }	
	
    /**
     * 系统是否开发调试状态(性能很低，便利于开发调试。同时sql的?替换输出，日志记录了sql真实数据)
     * 默认不是开发调试状态，是正式运行状态
     */
    private boolean systemDebugMode = true;
    
    /**
     * @return 系统是否开发调试状态(系统综合运行性能较低，优化了应用启动速度。同时sql的?替换输出，日志记录了sql真实数据)
     */
    public boolean isSystemDebugMode() {
        return systemDebugMode;
    }
    public void setSystemDebugMode(boolean systemDebugMode) {
        this.systemDebugMode = systemDebugMode;
    }
	
	/**
	 * primary key prefix of this cluster node, range: 1000-9222 
	        本集群节点RmIdFactory产生的主键前缀
	 */
	private String shardingPrefix = "1000";
	
    /**
     * @return 本集群节点RmIdFactory产生的主键前缀
     */
    public String getShardingPrefix() {
        return shardingPrefix;
    }
    public void setShardingPrefix(String shardingPrefix) {
        this.shardingPrefix = shardingPrefix;
    }

	/**
	 * 系统用户是否唯一登录，同时登录会强制踢出第一个用户
	 */
	private boolean userUniqueLogin = true;
    /**
     * @return 系统用户是否唯一登录，同时登录会强制踢出第一个用户
     */
    public boolean isUserUniqueLogin() {
        return userUniqueLogin;
    }
    public void setUserUniqueLogin(boolean userUniqueLogin) {
        this.userUniqueLogin = userUniqueLogin;
    }	

	
	/**
	 * 默认的分页条数，默认按15条分页，会被rm.xml/rm/RmJspHelper/pageSize覆盖
	 */
	private int defaultPageSize = 15;
    /**
     * @return 默认的分页条数，会被rm.xml/rm/RmJspHelper/pageSize覆盖
     */
    public int getDefaultPageSize() {
        return defaultPageSize;
    }
    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }	
	
	/**
	 * 默认的批处理条数，用于sql的union all条数
	 */
	private int defaultBatchSize = 10;
    /**
     * @return the defaultBatchSize
     */
    public int getDefaultBatchSize() {
        return defaultBatchSize;
    }
    public void setDefaultBatchSize(int defaultBatchSize) {
        this.defaultBatchSize = defaultBatchSize;
    }	
	
	/**
	 * 得到系统描述
	 */
	private String appDescription = "SecurityInJava by CipherGateway";
    /**
     * @return 得到系统简短描述
     */
    public String getAppDescription() {
        return appDescription;
    }
    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }	
	
	/**
	 * 是否记录request的执行时间和SQL数量
	 */
	private boolean logRequest = true;
    /**
     * @return 是否记录request的执行时间和SQL数量
     */
    public boolean isLogRequest() {
        return logRequest;
    }
    public void setLogRequest(boolean logRequest) {
        this.logRequest = logRequest;
    }
	
	/**
	 * Python的Lib目录不存在时，自动解压缩
	 */
	private boolean createPythonLibIfNotExist = true;
	
    /**
     * @return the createPythonLibIfNotExist
     */
    public boolean isCreatePythonLibIfNotExist() {
        return createPythonLibIfNotExist;
    }
    public void setCreatePythonLibIfNotExist(boolean createPythonLibIfNotExist) {
        this.createPythonLibIfNotExist = createPythonLibIfNotExist;
    }

	/**
	 * 是否逻辑删除文件
	 */
	private boolean logicDeleteFile = false;
	
    /**
     * @return the logicDeleteFile
     */
    public boolean isLogicDeleteFile() {
        return logicDeleteFile;
    }
    public void setLogicDeleteFile(boolean logicDeleteFile) {
        this.logicDeleteFile = logicDeleteFile;
    }	
	
	/**
	 * 逻辑删除的回收站文件夹
	 */
	private String recycleBinFolder = warHome + "/recycle_bin";
	
    /**
     * @return the recycleBinFolder
     */
    public String getRecycleBinFolder() {
        return recycleBinFolder;
    }
    public void setRecycleBinFolder(String recycleBinFolder) {
        this.recycleBinFolder = recycleBinFolder;
    }
	
	/**
	 * 是否记住当前url列表的在第几行
	 */
	private boolean rememberPage = false;
	
    /**
     * 是否记住当前url列表的在第几行
     * 
     * @return
     */
    public boolean isRememberPage() {
        return rememberPage;
    }
    public void setRememberPage(boolean rememberPage) {
        this.rememberPage = rememberPage;
    }	
	
	/**
	 * 默认字体
	 */
	private String defaultFont = null;
	
    /**
     * 默认字体
     * 
     * @return
     */
    public String getDefaultFont() {
        return defaultFont;
    }
    public void setDefaultFont(String defaultFont) {
        this.defaultFont = defaultFont;
    }
	
	/**
	 * 最多in多少条记录？
	 */
	private int maxSqlInCount = 1000;
	
    /**
     * 最多in多少条记录？
     * @return
     */
    public int getMaxSqlInCount() {
        return maxSqlInCount;
    }
    public void setMaxSqlInCount(int maxSqlInCount) {
        this.maxSqlInCount = maxSqlInCount;
    }
	
	/**
	 * 系统默认编码
	 */
    private String defaultEncode = "UTF-8";

    /**
     * 系统默认编码
     * @return
     */
    public String getDefaultEncode() {
        return defaultEncode;
    }
    public void setDefaultEncode(String defaultEncode) {
        this.defaultEncode = defaultEncode;
    }
	
	/**
	 * 默认实数数值的精度
	 */
	private int defaultNumberScale = 2;

    /**
     * 默认实数数值的精度
     * 
     * @return
     */
    public int getDefaultNumberScale() {
        return defaultNumberScale;
    }
    public void setDefaultNumberScale(int defaultNumberScale) {
        this.defaultNumberScale = defaultNumberScale;
    }

}
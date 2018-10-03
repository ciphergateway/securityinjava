package org.quickbundle.tools.helper;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.UUIDTimer;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class RmUUIDHelper {
	
	//全局单例
	private static UUIDTimer uuidTimer;
	//全局单例的初始化标记，用于双检锁安全判断
	private static volatile boolean isInitTimer = false;
	
    static UUIDTimer getDefaultTimer() {
        if(!isInitTimer) {
            synchronized (RmUUIDHelper.class) {
                if(!isInitTimer) {
            		try {
            			uuidTimer = new UUIDTimer(new java.util.Random(System.currentTimeMillis()), null);
            		} catch (IOException e) {
            			throw new IllegalArgumentException("RmUUIDHelper: Failed to create UUIDTimer with specified synchronizer: "+e.getMessage(), e);
            		}
                	isInitTimer = true;
                }
            }
        }
        return uuidTimer;
    }
	
	private static TimeBasedGenerator TB_GENERATOR = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	private static RandomBasedGenerator RD_GENERATOR = Generators.randomBasedGenerator();

	static UUID getRandomBasedUUID() {
		return RD_GENERATOR.generate();
	}

	static UUID getTimeBasedUUID() {
		return TB_GENERATOR.generate();
	}

	/**
	 * 默认全局获得uuid的唯一入口方法
	 * 
	 * @return
	 */
	public static String getUUID() {
		return getTimeBasedUUID().toString();
	}

	/**
	 * 从基于时间戳的uuid字符串，获得UUID专用时间戳
	 * 
	 * @param uuid
	 * @return
	 */
	public static long getTimestamp(String uuid) {
		UUID uu = UUID.fromString(uuid);
		return uu.timestamp();
	}

	/**
	 * 返回UUID格式的时间戳
	 * 
	 * @return
	 */
	public static long getSysTimestamp() {
		return getDefaultTimer().getTimestamp();
	}


}

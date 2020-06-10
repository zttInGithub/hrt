package com.hrt.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolAbstract;
import redis.clients.jedis.JedisPoolConfig;

public final class JedisSource {
	
	private static final Log log = LogFactory.getLog(JedisSource.class);
	//Redis服务器IP
	private static String host ;
	//Redis的端口号
	private static int port ;
    //访问密码
    private static String auth ;
    
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int max_active ;
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int max_idle ;
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int max_wait ;
    
    private static int timeOut; 
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean test_no_borrow ;
    
    private static JedisPool jedisPool = null;
    
    /**
     * 初始化Redis连接池
     */
    public JedisSource() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
//            config.setMaxActive(MAX_ACTIVE);
            config.setMaxTotal(max_active);
            config.setMaxIdle(max_idle );
            config.setMaxWaitMillis( max_wait );
            config.setTestOnBorrow(test_no_borrow);
            jedisPool = new JedisPool(config, host, port, timeOut, auth);
            
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
            	new JedisSource();
                return jedisPool.getResource();
            }
        } catch (Exception e) {
        	log.error(e);
            return null;
        }
    }
    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResourceObject(jedis);
        }
    }

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		JedisSource.host = host;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		JedisSource.port = port;
	}

	public static String getAuth() {
		return auth;
	}

	public static void setAuth(String auth) {
		JedisSource.auth = auth;
	}

	public static int getMax_active() {
		return max_active;
	}

	public static void setMax_active(int max_active) {
		JedisSource.max_active = max_active;
	}

	public static int getMax_idle() {
		return max_idle;
	}

	public static void setMax_idle(int max_idle) {
		JedisSource.max_idle = max_idle;
	}

	public static int getMax_wait() {
		return max_wait;
	}

	public static void setMax_wait(int max_wait) {
		JedisSource.max_wait = max_wait;
	}

	public static int getTimeOut() {
		return timeOut;
	}

	public static void setTimeOut(int timeOut) {
		JedisSource.timeOut = timeOut;
	}

	public static boolean isTest_no_borrow() {
		return test_no_borrow;
	}

	public static void setTest_no_borrow(boolean test_no_borrow) {
		JedisSource.test_no_borrow = test_no_borrow;
	}


	
//	public static void main(String[] args){
//		
//		JedisSource.setAuth("lpay");
//		JedisSource.setHost("10.51.29.216");
//		JedisSource.setMax_active("20");
//		JedisSource.setMax_idle("20");
//		JedisSource.setMax_wait("60000");
//		JedisSource.setPort("6379");
//		JedisSource.setTest_no_borrow("true");
//		
//		Jedis jedis = JedisSource.getJedis();
//		jedis.set("123", "321");
//		System.out.println(jedis.exists("123"));
//		jedis.del("123");
//		System.out.println(jedis.exists("123"));
//	}
}

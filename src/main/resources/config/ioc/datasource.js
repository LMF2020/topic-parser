var ioc = {
	    config : {// 读取配置文件
	        type : "org.nutz.ioc.impl.PropertiesProxy",
	        fields : {
	            paths : ["config/jdbc.property"]
	        }
	    },
        dataSource : {// 数据源
                type : "com.alibaba.druid.pool.DruidDataSource",
                events : {
                	depose : "close"
                },
                fields : {
                		driverClassName : {java : '$config.get("jdbc.driverClassName")'},
                        url : {java : '$config.get("jdbc.url")'},
                        username : {java : '$config.get("jdbc.username")'},
                        password : {java : '$config.get("jdbc.password")'},
                        filters : "stat"
                }
        },
        dao : {// Dao
            type : "org.nutz.dao.impl.NutDao",
            args : [{refer:"dataSource"}]
    }
};
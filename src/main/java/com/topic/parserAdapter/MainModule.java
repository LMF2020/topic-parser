package com.topic.parserAdapter;

import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.topic.parserAdapter.core.listener.ServerSetupListener;

/**
 * 配置Ioc容器并扫描ioc文件夹中的js文件,作为JsonLoader的配置文件
 * @author Rayintee
 *
 */
@IocBy(args = {
		"*org.nutz.ioc.loader.json.JsonLoader","config/ioc/datasource.js", 
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader","com.topic.parserAdapter"}, 
		type = ComboIocProvider.class)
@Modules(scanPackage=true)
@SetupBy(ServerSetupListener.class)
public class MainModule {
	
}

package eu.focos.performance;

import java.util.Map;

import com.ibm.commons.util.StringUtil;
import com.ibm.xsp.extlib.util.ExtLibUtil;

public abstract class Utils {
	
	private static final String defaultPath = "bs4xp/fakenames.nsf";		//default path to fakenames database;
	
	@SuppressWarnings("unchecked")
	public static String getTestDbPath() {
		
		String path = defaultPath;
		
		Map sessionScope = ExtLibUtil.getSessionScope();
		if (sessionScope.containsKey("dbPath") && StringUtil.isNotEmpty("" + sessionScope.containsKey("dbPath"))) {
			path = (String) sessionScope.get("dbPath");
		}
		return path;
		
	}

}

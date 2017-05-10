package eu.focos.performance;

import org.openntf.domino.Database;
import org.openntf.domino.Session;
import org.openntf.domino.View;
import org.openntf.domino.ViewEntry;
import org.openntf.domino.ViewNavigator;
import org.openntf.domino.utils.XSPUtil;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class ODA extends Base {

	private static final long serialVersionUID = 1L;
	
	public void go() {
		
		try {
			Timer timer = new Timer();
			timer.start();
			
			Session session = XSPUtil.getCurrentSession();
			Database dbData = session.getDatabase("", (String)ExtLibUtil.getSessionScope().get("dbPath"));
			
			View view = dbData.getView("People");
			ViewNavigator nav = view.createViewNav();
			
			nav.setEntryOptions(ViewNavigator.VN_ENTRYOPT_NOCOUNTDATA);
			nav.setCacheGuidance(400, ViewNavigator.VN_CACHEGUIDANCE_READALL);
			
			int i = 0;
			
			for(ViewEntry ve : nav) {
				i++;
				
				//@SuppressWarnings("unused")
				String userName = (String) ve.getColumnValue("$17", String.class);
				
				/*if (i % 5000 ==0) {
					System.out.println(i + userName);
				}*/

			}
			
			timer.stop();
			
			this.addResult(timer.getElapsedTimeMs() + " ms for " + i + " entries"); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

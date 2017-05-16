package eu.focos.performance;

import java.io.Serializable;

import org.openntf.domino.Database;
import org.openntf.domino.Session;
import org.openntf.domino.View;
import org.openntf.domino.ViewEntry;
import org.openntf.domino.ViewNavigator;
import org.openntf.domino.utils.XSPUtil;

public class ODAColIndexFaster implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public void go(String label) {

		try {
			
			Timer timer = new Timer(label);
			timer.start();
			
			Session session = XSPUtil.getCurrentSession();
			Database dbData = session.getDatabase("", Utils.getTestDbPath());
			
			View view = dbData.getView("People");
			ViewNavigator nav = view.createViewNav();
			
			nav.setEntryOptions(ViewNavigator.VN_ENTRYOPT_NOCOUNTDATA);
			nav.setCacheGuidance(400, ViewNavigator.VN_CACHEGUIDANCE_READALL);
			
			int i = 0;
			
			ViewEntry ve = nav.getFirst();
			while (null != ve) {
				i++;
				
				String userName = (String) ve.getColumnValues().get(1);
				
			/*	if (i % 5000 ==0) {
					System.out.println(i + " " + userName);
				}
*/
				ve = nav.getNext();
			}
			
			timer.stopAndSave();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

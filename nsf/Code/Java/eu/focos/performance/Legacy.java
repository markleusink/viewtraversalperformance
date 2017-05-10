package eu.focos.performance;

import lotus.domino.Database;
import lotus.domino.NotesException;
import lotus.domino.Session;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import lotus.domino.ViewNavigator;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class Legacy extends Base {
	
	private static final long serialVersionUID = 1L;

	public void go() {
		
		try {
			
			Timer timer = new Timer();
			timer.start();
			
			Session session = ExtLibUtil.getCurrentSession();
			Database dbData = session.getDatabase("", (String)ExtLibUtil.getSessionScope().get("dbPath"));
			
			View view = dbData.getView("People");
			view.setAutoUpdate(false);
			
			ViewNavigator nav = view.createViewNav();
			
			nav.setEntryOptions(ViewNavigator.VN_ENTRYOPT_NOCOUNTDATA);
			nav.setCacheGuidance(400, ViewNavigator.VN_CACHEGUIDANCE_READALL);
			
			int i = 0;
			
			ViewEntry ve = nav.getFirst();
			while (ve != null) {
				i++;
				
				String userName = (String) ve.getColumnValues().get(1);

				/*if (i % 5000 ==0) {
					System.out.println(i + userName);
				}*/
				
				ViewEntry tmp = nav.getNext(ve);
				ve.recycle();
				ve = tmp;
			}
			
			timer.stop();
			
			this.addResult(timer.getElapsedTimeMs() + " ms for " + i + " entries"); 
			
		} catch (NotesException e) {
			e.printStackTrace();
		}
		
	}

}

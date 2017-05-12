package eu.focos.performance;

import java.io.Serializable;

import lotus.domino.Database;
import lotus.domino.NotesException;
import lotus.domino.Session;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import lotus.domino.ViewNavigator;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class Legacy implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public void go(String label) {

		try {

			Timer timer = new Timer(label);
			timer.start();
			
			Session session = ExtLibUtil.getCurrentSession();
			Database dbData = session.getDatabase("", Utils.getTestDbPath());
			
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
				
				ViewEntry tmp = nav.getNext();
				ve.recycle();
				ve = tmp;
			}
			
			timer.stopAndSave();
			
		} catch (NotesException e) {
			e.printStackTrace();
		}
		
	}

}

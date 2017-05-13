package eu.focos.performance;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.concurrent.Callable;

import lotus.domino.Session;

import com.ibm.xsp.extlib.util.ExtLibUtil;
import com.mindoo.domino.jna.NotesCollection;
import com.mindoo.domino.jna.NotesDatabase;
import com.mindoo.domino.jna.constants.Navigate;
import com.mindoo.domino.jna.gc.NotesGC;

public class JNANoteIdsOnlyFaster implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public void go(String label) {

		try {

			Timer timer = new Timer(label);
			timer.start();
			
			NotesGC.runWithAutoGC(new Callable<Object>() {

				public Object call() throws Exception {
			
					Session session = ExtLibUtil.getCurrentSession();
					NotesDatabase dbData = new NotesDatabase(session, "", Utils.getTestDbPath());
				
					NotesCollection colFromDbData = dbData.openCollectionByName("People");
							
					LinkedHashSet<Integer> allIds = colFromDbData.getAllIds(Navigate.NEXT_NONCATEGORY);
					
					int i = 0;
					
					for (int noteId : allIds) {
						i++;
						
						/*if (i % 4000 ==0) {
							System.out.println(i + ": " + noteId);
						}
				*/
					}
					
					return null;
				}
				
				
			});

			timer.stopAndSave();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

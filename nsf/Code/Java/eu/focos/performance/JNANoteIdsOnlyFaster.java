package eu.focos.performance;

import java.io.Serializable;
import java.util.concurrent.Callable;

import lotus.domino.Session;

import com.ibm.xsp.extlib.util.ExtLibUtil;
import com.mindoo.domino.jna.NotesCollection;
import com.mindoo.domino.jna.NotesDatabase;
import com.mindoo.domino.jna.NotesIDTable;
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
					
long t0,t1;
					
					NotesIDTable idTableReadWithOptimizedCall = new NotesIDTable();
					
					{
						t0=System.currentTimeMillis();
						//method uses NIFGetIDTableExtended internally to quickly populate an ID table
						//with all ids found in a view
						colFromDbData.getAllIds(Navigate.NEXT_NONCATEGORY, true, idTableReadWithOptimizedCall);
						t1=System.currentTimeMillis();
						System.out.println("Reading "+idTableReadWithOptimizedCall.getCount()+" note ids via NIFGetIDTableExtended WITHOUT enumeration took "+(t1-t0)+"ms");

						//enumerate ids manually; calling NotesIDTable.toArray() does the same;
						//we just want to verify that the enumeration returns the right data
						/*final int[] allIds = new int[idTableReadWithOptimizedCall.getCount()];
						final int[] idx = new int[1];
						
						idTableReadWithOptimizedCall.enumerate(new NotesIDTable.IEnumerateCallback() {

							public Action noteVisited(int noteId) {
								allIds[idx[0]] = noteId;
								idx[0]++;

								return Action.Continue;
							}});
						
						t1=System.currentTimeMillis();
						System.out.println("Reading "+idTableReadWithOptimizedCall.getCount()+" note ids via NIFGetIDTableExtended WITH enumeration took "+(t1-t0)+"ms");
						*/
					}

		
						
						/*if (i % 4000 ==0) {
							System.out.println(i + ": " + noteId);
						}
				*/
					
					return null;
					
				}
				
				
			});

			timer.stopAndSave();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}

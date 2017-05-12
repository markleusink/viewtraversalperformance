package eu.focos.performance;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.Callable;

import lotus.domino.Session;

import com.ibm.xsp.extlib.util.ExtLibUtil;
import com.mindoo.domino.jna.NotesCollection;
import com.mindoo.domino.jna.NotesDatabase;
import com.mindoo.domino.jna.NotesViewEntryData;
import com.mindoo.domino.jna.NotesCollection.EntriesAsListCallback;
import com.mindoo.domino.jna.constants.Navigate;
import com.mindoo.domino.jna.constants.ReadMask;
import com.mindoo.domino.jna.gc.NotesGC;

public class JNANoteIdsOnly implements Serializable{
	
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
		
					String startPos = "0";
					int entriesToSkip = 1;
		
					EnumSet<Navigate> returnNavigator = EnumSet.of(Navigate.NEXT);
					EnumSet<ReadMask> returnData = EnumSet.of(ReadMask.NOTEID);
		
					List<NotesViewEntryData> allEntries = colFromDbData.getAllEntries(startPos, entriesToSkip, 
							returnNavigator, Integer.MAX_VALUE,
							returnData, new EntriesAsListCallback(Integer.MAX_VALUE));
					
					int i = 0;
					
					for (NotesViewEntryData currEntry : allEntries) {
						i++;
						
						int noteId = currEntry.getNoteId();
			/*			
						if (i % 5000 ==0) {
							System.out.println(i + ": " + noteId);
						}*/
				
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

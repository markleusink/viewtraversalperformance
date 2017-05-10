package eu.focos.performance;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;

import lotus.domino.Session;

import com.ibm.xsp.extlib.util.ExtLibUtil;
import com.mindoo.domino.jna.NotesCollection;
import com.mindoo.domino.jna.NotesDatabase;
import com.mindoo.domino.jna.NotesViewEntryData;
import com.mindoo.domino.jna.NotesCollection.EntriesAsListCallback;
import com.mindoo.domino.jna.constants.Navigate;
import com.mindoo.domino.jna.constants.ReadMask;

public class JNA implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public void go(String label) {

		try {

			Timer timer = new Timer(label);
			timer.start();
			
			Session session = ExtLibUtil.getCurrentSession();
			NotesDatabase dbData = new NotesDatabase(session, "", (String) ExtLibUtil.getSessionScope().get("dbPath"));
		
			NotesCollection colFromDbData = dbData.openCollectionByName("People");

			String startPos = "0";
			int entriesToSkip = 1;

			EnumSet<Navigate> returnNavigator = EnumSet.of(Navigate.NEXT);
			EnumSet<ReadMask> returnData = EnumSet.of(ReadMask.NOTEID, ReadMask.SUMMARY);

			List<NotesViewEntryData> allEntries = colFromDbData.getAllEntries(startPos, entriesToSkip, 
					returnNavigator, Integer.MAX_VALUE,
					returnData, new EntriesAsListCallback(Integer.MAX_VALUE));
			
			int i = 0;
		
			for (NotesViewEntryData currEntry : allEntries) {
				i++;
				
				
				String userName = (String) currEntry.get("$17");
				
				/*if (i % 5000 ==0) {
					System.out.println(i + userName);
				}*/

			}

			timer.stopAndSave();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

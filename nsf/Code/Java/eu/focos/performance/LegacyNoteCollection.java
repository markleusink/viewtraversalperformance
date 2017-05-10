package eu.focos.performance;

import java.io.Serializable;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.NoteCollection;
import lotus.domino.NotesException;
import lotus.domino.Session;

import com.ibm.commons.util.StringUtil;
import com.ibm.xsp.extlib.util.ExtLibUtil;

public class LegacyNoteCollection implements Serializable {

	private static final long serialVersionUID = 1L;

	public void go(String label) {

		try {

			Timer timer = new Timer(label);
			timer.start();

			Session session = ExtLibUtil.getCurrentSession();
			Database dbData = session.getDatabase("", (String) ExtLibUtil.getSessionScope().get("dbPath"));

			// construct a NoteCollection on the fly using the same selection formula as in the view (People)
			NoteCollection nc = dbData.createNoteCollection(false);
			nc.selectAllDataNotes(true);
			nc.setSelectionFormula("Form=\"Person\"");
			nc.buildCollection();
			
			String noteId = nc.getFirstNoteID();
			while (!StringUtil.isEmpty(noteId)) {
				Document doc = dbData.getDocumentByID(noteId);
				noteId = nc.getNextNoteID(noteId);

				if (doc.isValid()) {

					String lastName = doc.getItemValueString("LastName");
					String middleInitial = doc.getItemValueString("MiddleInitial");
					String firstName = doc.getItemValueString("FirstName");
				}
				doc.recycle();
			}

			timer.stopAndSave();

		} catch (NotesException e) {
			e.printStackTrace();
		}

	}

}

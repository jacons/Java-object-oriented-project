package NoOffensiveMicroBlog;

import java.util.Calendar;
import java.util.Date;
import NoOffensiveInterfaces.IReport;
import exceptions.RepresentationInvariantException;

public class Report implements IReport {
	
	private final Calendar data;
	private final String supervisor;
	private final String comment;
	
	
	private void repOk() {
			
		/** Representation Invariant
		 * Logic data != null || supervisor != null || comment != null || comment != ""
		 */
		try {
			if(data == null || supervisor == null || comment == null || comment.trim().length()==0)
				throw new RepresentationInvariantException("obj contains some null elements");	
			
		} catch (RepresentationInvariantException e) {
			System.out.println("Errore nell'invariante di rappresentazione"+e.getMessage());
			System.exit(1); 
		}
		
	}

	public Report(Calendar data, String supervisor, String comment) {

		if(data == null || supervisor == null || comment == null)
			throw new NullPointerException();
		
		if(comment.isEmpty()) throw new IllegalArgumentException("comment is empty");
		
		this.data = data;
		this.supervisor = supervisor;
		this.comment = comment;
		
		repOk();
	}
	
	@Override
	public Date getData() {
		return (Date) data.clone();
	}
	@Override
	public String getSupervisor() {
		return supervisor;
	}
	@Override
	public String getComment() {
		return comment;
	}	
}
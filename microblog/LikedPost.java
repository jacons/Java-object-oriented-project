package microblog;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import exceptions.LikeNotAllowedExeption;
import exceptions.RepresentationInvariantException;
import exceptions.TextOverflowException;
import interfaces.ILikePostable;

public class LikedPost extends Post implements ILikePostable {
	
	private Set<String> liked; 
	
	private void repOk() {
		
		// I add controll only on the new structure
		/**
		 * Logic : 
		 * liked != null ∧ (∀s ∈ liked : s != null ∧ s!= author)
		 * 
		 */
		try {
			
			if(liked == null) throw new RepresentationInvariantException(" liked is null");
			
			String author  = this.getAuthor();
			
			for(String s : liked) if (s==null || s.equals(author))
				throw new RepresentationInvariantException(" liked list contains null element or the author of post");
			
		} catch (RepresentationInvariantException e) {
			System.out.println("Errore nell'invariante di rappresentazione"); System.exit(1); 
		}
	}

	public LikedPost(String author, String text, int id, Calendar cal, Set<String> tagged) 
			throws TextOverflowException {
		
		super(author, text, id, cal, tagged);
		liked = new HashSet<String>();
		
		repOk();
	}

	@Override
	public boolean addLike(String s) throws LikeNotAllowedExeption {

		if(s == null) throw new NullPointerException(" user cannot be null");
		
		if(s.compareTo(this.getAuthor())==0) 
			throw new LikeNotAllowedExeption(" user cannot like their own post");
		
		boolean result = liked.add(s);
		
		repOk();
		
		return result;
	}
	@Override 
	public boolean equals(Object o) { 
		return super.equals(o);
	}
	
	@Override
	public Set<String> getLike() {
		// the same concept as GetMenzioned()
		return Collections.unmodifiableSet(liked);
	}
	@Override
	public String toString() {
		return super.toString()
				+"	-->LIKE : "+getNumLike()
				+"\n-----";
	}
	@Override
	public int getNumLike() {
		return liked.size();
	}
	
	
}

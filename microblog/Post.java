package microblog;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import exceptions.RepresentationInvariantException;
import exceptions.TextOverflowException;
import interfaces.IPost;

public class Post implements IPost {
	
	private final int id;
	private final String author;
	private final String text;
	private final Calendar date;
	private final Set<String> tagged;
	
	private void repOk() {
		
		/**
		 *  Logic :
		 *  id >= 0 ∧ author != null && 
		 *  text != null && text.length <= 140 ∧ 
		 *  date == null ∧ tagged != null ∧
		 *  (∀p ∈ tagged : p !== null ∧ p != author )
		 * 
		 */
		try {
			if(id<0) throw new RepresentationInvariantException(" 'id' is negative");
			
			if( author == null || text == null || date == null || tagged == null)
				throw new RepresentationInvariantException(" some elements are null");
			
			if(text.length()>140) throw new RepresentationInvariantException(" text overflow");

			for(String s :tagged) if(s == null || s.compareTo(author)==0) 
				throw new RepresentationInvariantException(" in 'tagged' set some elements are null");
			
		} catch (RepresentationInvariantException e) {
			System.out.println("Errore nell'invariante di rappresentazione"); System.exit(1); 
		}
	}
	
	public Post(String author,String text,int id,Calendar cal,Set<String> tags) 
			throws TextOverflowException,NullPointerException,IllegalArgumentException
	{
		if(author == null) 
			throw new NullPointerException("The author cannot be null");
		if(text == null)
			throw new NullPointerException("text cannot be null"); 
		if(text.trim().length() == 0)
			throw new IllegalArgumentException("text cannot be empty");
		if(text.length()>140)             
			throw new TextOverflowException("The message text exceeds the number of characters allowed"); 		
		if(tags == null)
			throw new NullPointerException("tags cannot be null"); 
		
		for(String s : tags) {
			if(s == null)
				throw new NullPointerException("The menzioned user cannot be null"); 
			if(s.compareTo(author)==0) 
				 throw new IllegalArgumentException("In a post you cannot mention yourself");
		}
		
		this.id = id;
		this.author = author;
		this.text = text;
		this.date = cal;
		this.tagged = new HashSet<String>(tags);
		
		repOk();
	}
	@Override
	public int getLenghtText() { return text.length(); }
	@Override
	public String getAuthor() { return author; }
	@Override
	public int getId() { return id; }
	@Override
	public String getText() { return text;}
	@Override
	public Set<String> getMenzioned() { 
		// the list can't be modified, an external user can't alter the characteristics
		// of the list so as to invalidate the RI.
		return Collections.unmodifiableSet(tagged); 
	}
	@Override
	public String[] sortedexplodetext() {
		
		String[] word = text.split(" ");
		Arrays.sort(word);
		return word;
	}
    @Override
	public String toString() {
		return "ID "+getId() 
				+ "\n	-->TESTO :"+this.text
				+ "\n	-->MENZIONATI"+tagged.toString()+"\n";
	}
    @Override
	public boolean equals(Object o) { 
		// If the object is compared with itself then return true   
	    if (o == this) { return true; } 
	    if (!(o instanceof Post)) { return false; }    
	    Post c = (Post) o; 
	          
	    return author.compareTo(c.author) == 0
	                && text.compareTo(c.text) == 0; 
	}
    @Override
    public int hashCode() {
        return author.hashCode()+text.hashCode();
    }
    
    public Object MyClone() throws TextOverflowException {
		return new Post(author, text, id, (Calendar) date.clone(), new HashSet<String>(tagged));
    }
 }
	
package microblog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import exceptions.LikeNotAllowedExeption;
import exceptions.PostNotFoundException;
import exceptions.RepresentationInvariantException;
import exceptions.TextOverflowException;
import exceptions.UserNotFoundException;
import interfaces.ISocial;
import interfaces.Populable;

public class Social implements ISocial,Populable {
	
	
    /*
    *  Funzione D'astrazione : 
    *  g(Network,Posts) = (Network: (String(user)) -> (set seguiti)
    *                      Dato u user nella rete f(u) = { seguiti da u } )
    *                                              
    *                     (Posts: (String(author))->(set Post),
    *                      Dato a user nella rete  f(a)= { post di a | ∃p.author ∈ Network.keySet} )
    */
	
	// Represents the next post-id
	private int nextpost = 0 ;
	// Represents the data structure of the social network
	private Map<String, Set<String>> Network;
	// Represents the data structure of the network posts, (grouped by authors)
	private Map<String,Set<LikedPost>> Posts;
	// Represents a Posts-auxiliary structure to iterate all posts.
	private List<LikedPost> listpost;

	private void repOk() {
		/*
		Representation Invariant : 
		
		(∀<user,followed> in Network.entrySet() : user != "" ∧
														 !followed.contain(user)  ∧
														 !followed.contain(null)  ∧ 
														 !followed.contain("")    ∧
														 (∀f ∈ followed : NetWork.containsKey(f))) ∧
		
		(∀<author,likedposts> ∈ Posts.entrySet() : author != null                   ∧
													       Network.containsKey(author)      ∧	
													       !likedposts.contaisKey(null)     ∧
													       (∀p ∈ likepost : 
													       					∀(user ∈ p.getLike() :
													       					  	Network.get(user).contains(author))) ∧
													       (∀p ∈ likedposts : p != null  ∧
													  						p.getAuthor() == author)) ∧
	 	 		
	 	(∀p1 ∈ listpost : Posts.get(p.getAuthor()).contains(p1) ∧
	 							  ∀(p2 ∈ listpost : p1.getId()!=p2.getId() ∧
	 							   						   !p1.equals(p2))) ∧
	 							   						   
	 	Il numero di post di listpost è uguale alla somma totale del numero dei post nei singoli 
	 	insiemi in Posts ,raggruppati per autore.
	 	
	 	*/
		try {
				
			for( Map.Entry<String,Set<String>> record  : Network.entrySet()) {
				
				if( record.getKey().trim().length() == 0 ||
					record.getValue().contains(record.getKey()) ||
					record.getValue().contains("")) throw new RepresentationInvariantException(" contains forbidden values") ;
				
				for(String flw : record.getValue()) if(!Network.containsKey(flw)) 
					throw new RepresentationInvariantException(" contains forbidden values") ;	
			}
			
			int tot=0;
			for( Map.Entry<String,Set<LikedPost>> record  : Posts.entrySet()) {
			
				if ( record.getKey() == null ||
					 !Network.containsKey(record.getKey()) ||
					 record.getValue().contains(null) ) throw new RepresentationInvariantException(" contains forbidden values") ;
					
			    for(LikedPost p : record.getValue()) {
			    	
					for(String user : p.getLike()) {
						if(Network.containsKey(user) && !Network.get(user).contains(record.getKey())) 
							throw new RepresentationInvariantException(" contains forbidden values") ;
					}
					
			    	if( p == null || !p.getAuthor().equals(record.getKey()))
			    		throw new RepresentationInvariantException(" contains forbidden values") ;	
			    }	
			    tot += record.getValue().size();
			}
			for(LikedPost p1 :listpost) {
				
				if (Posts.containsKey(p1.getAuthor()) && !Posts.get(p1.getAuthor()).contains(p1)) 
					throw new RepresentationInvariantException(" contains forbidden values");
				
				for(LikedPost p2 :listpost) {
					if (p1.getId() == p2.getId() && !p1.equals(p2))
						throw new RepresentationInvariantException(" contains forbidden values") ;	
				}
			}
			if (tot != listpost.size()) throw new RepresentationInvariantException(" contains forbidden values") ;	
	
			
		} catch (RepresentationInvariantException e) {
			System.out.println("Errore nell'invariante di rappresentazione"+e.getMessage());
			System.exit(1); 
		}
		
	}

	public Social() {
		
		// Allocate data structures 
		Network = new TreeMap<String,Set<String>>();
		
		Posts =   new TreeMap<String,Set<LikedPost>>();
		
		listpost = new ArrayList<LikedPost>();
		
		// Check representation invariant
		repOk();
	}

	protected boolean NetworkContains(String par) {
		return Network.containsKey(par);
	}

	protected boolean PostContains(int id) {	
		
		for(Post p : listpost) if(p.getId() == id) return true;
		
		return false;
	}
	@Override
	public boolean signup(String user) throws NullPointerException, IllegalArgumentException {
		
		
		if(user == null) 
			throw new NullPointerException(" The user cannot be null"); 
		
		if(user.trim().length() == 0) 
			throw new IllegalArgumentException(" user name cannot be empty");
		
		boolean result = false; 	
		
		// if not exist => I create new user
		if(!NetworkContains(user)) {
			result = true;
			// Allocate a followed-set
			Network.put(user,new TreeSet<String>());
		}
		
		// Check representation invariant
		repOk();
		
		return result;
	}
	
	@Override
	public boolean signup(String user, Set<String> follower)
			throws NullPointerException, IllegalArgumentException, UserNotFoundException {
		
		
		// follower must be user of social		
		for(String f : follower) if(!NetworkContains(f)) 
			throw new UserNotFoundException(" The user in the follower list is not registered on the social network");
		
		// I create new user. if already exist => clear a followed-set
		if(!signup(user)) Network.get(user).clear();
	
		// foreach element in follower : add user in followed-set
		Network.get(user).addAll(follower);
		
		// Check representation invariant
		repOk();
		
		return true;
	}

	@Override
	public boolean follow(String user, Set<String> follower)
			throws NullPointerException, IllegalArgumentException, UserNotFoundException {

		
		if(user == null)
			throw new NullPointerException(" The user cannot be null"); 
		
		if(follower == null)
			throw new NullPointerException(" Follower cannot be null"); 
		
		if(!NetworkContains(user)) 
			throw new UserNotFoundException(" The user is not registered on the social network");

		
		for(String f : follower) {
			if(f == null) 
				throw new NullPointerException(" The followed cannot be null"); 
			if(f.trim().length() == 0) /* si potrebbe anche evitare visto il controllo sottostante */
				throw new IllegalArgumentException(" follower name cannot be empty");
			if(!NetworkContains(f)) 
				throw new UserNotFoundException(" The user in the follower list is not registered on the social network");
			if(f.compareTo(user)==0)
				throw new IllegalArgumentException(" User cannot follow himself");
			
			// add followed in follower-set
			Network.get(user).add(f);
		}
		// Check representation invariant
		repOk();
		
		return true;
	}

	@Override
	public Set<String> getMentionedUsers() {
		
		// not allow duplicate
		Set<String> result = new TreeSet<String>();
		
		// I iterate all post and gets a list of tag-user , (duplicate are ignored)
		for(Post single : listpost) result.addAll(single.getMenzioned());
		
		return result;
	}
	
	/**
	 * Restituisce l’insieme degli utenti menzionati (inclusi) nella lista di post ps gli autori 
	 * dei post non devono necessariamente essere iscritti al social 
	 * @param ps List di post
	 * @requires ps != null && for all(p ∈ ps : p != null)
 	 * @effects true
 	 * @modifies null 
	 * @return for all (u ∈ obj : ∃(p ∈ POST : u ∈ ismenzioned(p))) 
	 * @throws NullPointerException if ps is null
	 */
	public static Set<String> getMentionedUsers(List<Post> ps) throws NullPointerException {
			
		if(ps == null) throw new NullPointerException(" ps cannot be null");
		
		// not allow duplicate
		Set<String> result = new TreeSet<String>();
		
		// I iterate all post and gets a list of tag-user , (duplicate are ignored)
		for(Post single : ps) {
			if (single == null) throw new NullPointerException(" post in list ps cannot be null");
			result.addAll(single.getMenzioned());
		}
		
		return result;	
		
	}
	
	@Override
	public List<String> influencers() {
		
		// I create a data structure : Set of <User,n. of follower>
		HashMap<String,Integer> influencer = new HashMap<String,Integer>();
		// Data structure which maintains the ordering of the data
		LinkedHashMap<String,Integer> sortedinfluencer = new LinkedHashMap<>();
		
		for(Map.Entry<String,Set<String>> record  : Network.entrySet()) {
			
			record.getValue().forEach((String user) -> {
				
				influencer.putIfAbsent(user, 0);	
			    influencer.computeIfPresent(user,(k, n) -> n + 1 );				
			});	
		}
		// Sort sortedinfluencer by number of followers using stream
		influencer.entrySet().stream().sorted(Map.Entry
									  .comparingByValue(Comparator.reverseOrder()))
	    							  .forEachOrdered(x -> sortedinfluencer.put(x.getKey(), x.getValue()));
	
		return new ArrayList<String>(sortedinfluencer.keySet());
	}
	
	
	@Override
	public Map<String, Set<String>> guessFollowers(List<Post> ps) throws NullPointerException {
		
		
		if(ps == null ) throw new NullPointerException();
		
		Map<String, Set<String>> result = new TreeMap<String,Set<String>>();
	
		String author;
		for(Post p : ps) {
			
			if(p == null) throw new NullPointerException();
			
			author = p.getAuthor();
			
			if(NetworkContains(author) && !result.containsKey(author))
				result.put(author,new TreeSet<String>(Network.get(author)));
					
		}
		
		return result;
	}

	public void debugAllUser() {
		for(String s : Network.keySet()) {
			System.out.println("utente "+s);
			for (String f: Network.get(s)) System.out.println("	segue "+f);
		}
	}
	//----------------------------	GESTIONE UTENTI DEL SOCIAL --------------------------------------

	//----------------------------	GESTIONE DEI POST -----------------------------------------------
	@Override
	public boolean post(String author, String text, Set<String> tags)
			throws NullPointerException, UserNotFoundException, IllegalArgumentException,TextOverflowException {
		
		
		if(!NetworkContains(author))  
			throw new UserNotFoundException(" The author is not registered on the social network");
			
		if (tags == null) throw new NullPointerException(" tags cannot be nulls");
		
		for(String mzd : tags) {
			if(!NetworkContains(mzd))
				throw new UserNotFoundException(" The user in the tags list is not registered on the social network"); 
		}	

		boolean isnew = true;
		
		// find another post with same "text" , obviously I look for  correspondence between the posts of an author "author"
		if(Posts.containsKey(author)) for(LikedPost p: Posts.get(author)) {
			
			// I dont use "equals", because I know which author I'm talking about.
			if(p.getText().compareTo(text)==0) {
				isnew = false; break;
			}
			
		}
		
		if (isnew) {
			
			// pointer of new post maybe lunch a exception 
			LikedPost p = new LikedPost(author, text, nextpost,Calendar.getInstance(),tags) ;
			nextpost++;
			//if don't exists ,I create <key,value> and I initialize author with empty list
			Posts.putIfAbsent(author,new HashSet<LikedPost>());
		
			// add pointer in TreeMap (aliasing)
			Posts.get(author).add(p);
			//  if was added I add pointer in list (aliasing)
			listpost.add(p);
			// Check representation invariant			
		}
		
		repOk();
		
		return isnew;
	}

	//----------------------------	GESTIONE DEI POST -----------------------------------------------	
	
	@Override
	public void PublishPostById(int id) throws PostNotFoundException, IllegalArgumentException {
		
		if(id<0) throw new IllegalArgumentException();
		
		for(LikedPost p : listpost) {
			
			if(p.getId() == id) {
				
				System.out.println("-------------------------");
				System.out.println("POST "  +p.getId());
				System.out.println("AUTORE "+p.getAuthor());
				System.out.println("TESTO " +p.getText());
				System.out.println("MENZIONATI "+p.getMenzioned().toString());
				System.out.println("LIKE "+p.getNumLike());
				System.out.println("-------------------------");
				
				break;
			}
		}	
	}

	@Override
	public boolean like(String user, int id)
			throws NullPointerException, IllegalArgumentException, UserNotFoundException, PostNotFoundException,LikeNotAllowedExeption {
		
		if(user == null ) 
			throw new NullPointerException(" user cannot be null"); 
		
		if(id < 0 )
			throw new IllegalArgumentException(" id must be positive or zero");
	
		if(!NetworkContains(user))
			throw new UserNotFoundException(" 'User' must be user in the social");
		
		LikedPost post = null ;
		
		for(LikedPost p : listpost) {
			if(p.getId() == id) { post = p; break; }
		}
		
		if (post==null) throw new PostNotFoundException(" id-post not found");
		
	
		if(!Network.get(user).contains(post.getAuthor()))
			throw new LikeNotAllowedExeption(" 'User' must follow the author of post");
		
	
		post.addLike(user);
		
	
		// Check representation invariant
		repOk();
		
		return true;
	}

	@Override
	public List<Post> writtenBy(String username) throws NullPointerException, TextOverflowException {

		
		if(username == null)
			throw new NullPointerException(" The username cannot be null"); 
		
		List<Post> post = new ArrayList<Post>();
				
		// add into return obj a clone of post (to avoid invalidating the invariant of representation)
		if(Posts.containsKey(username))
		for(Post p : Posts.get(username)) post.add((Post) p.MyClone());
		
		return post;
	}
	
	/**
	 * Restituisce una nuova lista completa dei post effettuati dall’utente nella rete sociale 
	 * il cui nome e la lista di post sono dati come parametro, gli autori non devono essere necessariamente
	 * iscritti al social.
	 * @param ps
	 * @param username
	 * @requires username != null ∧ ps != null ∧ for all(p ∈ ps : p != null)
	 * @effects true
	 * @modifies null 
	 * @return for all (p ∈ obj : authorOf(p) = username)
	 * @throws NullPointerException if ps is null or username is null
	*/
	public static List<Post> writtenBy(List<Post> ps, String username) throws NullPointerException, TextOverflowException {

		if(username == null)
			throw new NullPointerException(" The username cannot be null"); 
		if(ps == null)
			throw new NullPointerException(" ps cannot be null"); 
		
		List<Post> post = new ArrayList<Post>(ps.size());
		
		for(Post p : ps) {
			
			if (p == null)
				throw new NullPointerException(" post in list ps cannot be null"); 
			
			if(p.getAuthor().compareTo(username)==0) post.add((Post) p.MyClone());
			
		}
		
		return post;
	}

	@Override
	public List<Post> containing(List<String> words) throws NullPointerException, TextOverflowException {
	
		if(words == null)
			throw new NullPointerException(" words cannot be null"); 
		
		// theta(M) M = num of words 
		for(String s : words) if(s == null)
			throw new NullPointerException(" a word cannot be null"); 
		
		String[] textword;
		List<Post> result = new ArrayList<Post>();
		
		for(Post p : listpost) {
			
			// O(N*log(N)) Quicksort N = num of word into post
			textword = p.sortedexplodetext();
			
			// foreach word in "words" O(M)
			for(String s : words)  {
				// seach word s in the post word array theta(logN)
				if(Arrays.binarySearch(textword,s)>0) {
					result.add((Post) p.MyClone());
					break;
				}
			}	
		}
		// O( num of post * (M*log(N)+N*log(N)) )
		return result;
	}
	public void debugAllPost() {
		for(String s : Posts.keySet()) {
			System.out.print("UTENTE : ");
			System.err.println(s);
			for (Post p: Posts.get(s)) System.out.println("	-->"+p);
			System.out.println("\n");
		}
	}		
	//----------------------------	GESTIONE DEI POST -----------------------------------------------	

}
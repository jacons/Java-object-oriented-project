package NoOffensiveMicroBlog;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import NoOffensiveInterfaces.INoOffensiveSocial;
import exceptions.PostNotFoundException;
import exceptions.RepresentationInvariantException;
import exceptions.SupervisorNotFoundException;
import exceptions.UserNotFoundException;
import microblog.Social;

public class NoOffensiveSocial extends Social implements INoOffensiveSocial {
	
	/*
	 * Abstraction Function: g(OffensivePost,Supervisor) = 
	 * 
	 * 			(OffensivePost : (Integer)->(Report) 
	 * 				Data un intero i che rappresenta un post f(i) = <Informazione sulla segnalazione del post>)
	 * 
	 * 			(Supervisor : ()-> (Set String)
	 * 				f() = {superviors : ∀s ∈ superviors -> s ∈ Network.keySet()} 			
	 *
	*/
	private TreeMap<Integer,Report> OffensivePost;
	
	private HashSet<String> Supervisor;
	
	
	private void repOk() {
		/*
		 * Representation Invariant
		 * Logic: for all (user in Supervisor :  user != "" && && Networkcontains(user)) &&
		 * 		  for all (<id,info> in OffensivePost.entrySet() : id >= 0 && PostContains(id) &&
		 * 														   info != null)
		 */
			
		try {
			for(String s : Supervisor) {
				if(s.isEmpty() || !NetworkContains(s)) throw new RepresentationInvariantException(" some elements are empty");
			}
			
			for(Map.Entry<Integer, Report> record : OffensivePost.entrySet()) {
				
				if(record.getKey()<0 || !PostContains(record.getKey()) || record.getValue() == null) 
					throw new RepresentationInvariantException(" contains forbidden values");		
			}
			
		} catch (RepresentationInvariantException e) {
			System.out.println("Errore nell'invariante di rappresentazione"+e.getMessage());
			System.exit(1); 
		}
		
	}


	public NoOffensiveSocial() {
		super();
		
		OffensivePost = new TreeMap<Integer,Report>();
		Supervisor =    new HashSet<String>();
		repOk();
	}
	

	@Override
	public boolean Supervisor(String user) throws  NullPointerException,UserNotFoundException {
		
		if(user == null) throw new NullPointerException(" User cannot be null");
		
		if(!NetworkContains(user)) throw new UserNotFoundException(" The user is not registered on the social network");
		
		boolean r = Supervisor.add(user);
		
		repOk();
		return r;
	}


	@Override
	public boolean Report(String user, int post, String comment)
			throws SupervisorNotFoundException, PostNotFoundException, IllegalArgumentException {
		
		if(user == null || comment == null) throw new NullPointerException(" User or comment cannot be null");
		
		if(!Supervisor.contains(user)) throw new SupervisorNotFoundException(" The supervior is not registered on the social network");	
		
		if(post < 0 || comment.trim().length()==0 ) throw new IllegalArgumentException(" post must be positive, comment cannot be empty");
		
		if(!PostContains(post)) throw new PostNotFoundException(" post don't exist");
		
		OffensivePost.put(post,new Report(Calendar.getInstance(), user, comment));
		
		
		repOk();	
		return false;
	}

	@Override
	public void PublishPostById(int id) throws PostNotFoundException,IllegalArgumentException {
		
		super.PublishPostById(id);
		
		Report info = OffensivePost.get(id);
		if(info!=null) System.err.print("Questo post è stato segnalato: "+info.getComment());
		
	}	
}

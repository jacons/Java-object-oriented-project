package NoOffensiveInterfaces;

import exceptions.PostNotFoundException;
import exceptions.SupervisorNotFoundException;
import exceptions.UserNotFoundException;

public interface INoOffensiveSocial {
	
	/* 
	 * Overview: NoOffensiveSocial è una estenzione di Social nella quale sono 
	 * 			 presenti un insieme modificabile di supervisori ed un insieme di segnalazioni
	 * 		     a post ritenuti dai supervisori offensivi.
	 * 
	 * Typical Element : SUPERVISOR, un insieme di utenti del social che effettuano le segnalazioni.
	 *                   REPORTEDPOST : {(id,info),(id,info)....} un insieme di coppie che rapprentano
	 *                   				il post segnalato ed il suo relativo motivo.
	 */
	
	/**
	 * Aggiunge l'utente all'insieme dei supervisori dei post, se è gia presente lo ignora
	 * @param user
	 * @requires user != null && user ∈ UTENTI
	 * @effects true
	 * @modifies this 
	 * @return true if user was added, false otherwise 
	 * @throws UserNotFoundException
	 */
	public boolean Supervisor(String user) throws NullPointerException,UserNotFoundException;
	
	/**
	 * Segnala un post che ha come id "post", aggiungendo un commento sul motivo della segnalazione,
	 * solo chi è un supervisiore può segnalare un post.
	 * @param user
	 * @param comment
	 * @requires user != null && user ∈ SUPERVISOR &&
	 				 post >= 0 && comment != null && comment.lenght != 0 &&
	 				 ∃(p ∈ POST : isPostId(id)) 
	 * @effects REPORTEDPOST = id U REPORTEDPOST
	 * @return 
	 * @throws UserNotFoundException
	 * @throws PostNotFoundException
	 * @throws IllegalArgumentException
	 */
	public boolean Report(String user,int post,String comment) 
			throws SupervisorNotFoundException,PostNotFoundException,IllegalArgumentException ;
	
	/**
	 * Restituisce le informazioni del post formattate recuperato dall'id dato come parametro,
	 * informa ulteriormente se il post è stato segnalato
	 * @param id 
	 * @requires id >= 0
	 * @effects true
	 * @modifies null 
	 * @throws PostNotFoundException if Post not found
	 * @throws IllegalArgumentException if id < 0 
	 */
	public void PublishPostById(int id) throws PostNotFoundException,IllegalArgumentException;
	
}

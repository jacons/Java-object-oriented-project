package interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.PostNotFoundException;
import exceptions.TextOverflowException;
import microblog.Post;

public interface ISocial {
	
	
	
	/*
	 * Overview: Social è una coppia formata da una collezione mutabile di post e da un
	 * 		     insieme modificabile di utenti iscritti al social i quali possono seguire utenti e 
	 * 			 aggiungere reazioni a post.

	 * Typical Element : UTENTI {<utente_1>...<utente_n>}, un insieme modificabile di utenti.
	 * 
	 * 					 POSTS {<autore1,{post1..postn}>...<autore2,{post1..postn}>},
	 *  				 		una mappa che associa un autore ad una serie di post.
	 *  
	 *  				 FOLLOWER(u), l'insieme modifiabile di utenti seguiti da un l'utente u.
	 *  
	 *  
	 *  Nella relazione sono estesti alcuni concetti tralasciati qui.
	 */
	

	/**
	 * Restituisce la lista dei post effettuati dall’utente nella rete sociale il cui nome
	 * è dato dal parametro username.
	 * @param username
	 * @requires username != null
	 * @effects true 
	 * @modifies null 
	 * @return (∀p ∈ obj : authorOf(p) = username) 
	 * @throws NullPointerException if username is null
	 * @throws TextOverflowException 
	 */
	public List<Post> writtenBy(String  username) throws NullPointerException, TextOverflowException;

	/**
	 * Restituisce l’insieme degli utenti menzionati (inclusi) nei post presenti nella rete sociale.
	 * @requires true
	 * @effects true
 	 * @modifies null 
	 * @return (∀ u ∈ obj : ∃(p ∈ POST : u ∈ ismenzioned(p)))  
	 */
	public Set<String> getMentionedUsers();

	/**
	 * Restituisce tutti gli utenti delle rete sociale, in ordine
	 * di "influenza" ovvero in base al numero di “follower” che siano maggiori a 0;
	 * @requires true
	 * @effects true 
	 * @modifies null 
	 * @return restituisce un lista di utenti.
	 */	
	public List<String> influencers();

	/**
	 * Restituisce la lista dei post presenti nella rete sociale che includono almeno
	 * una delle parole presenti nella lista delle parole argomento del metodo.
	 * @param words
	 * @requires words != null ∧ ∀ (w ∈ words : w!= null)
 	 * @effects true
 	 * @modifies null 
	 * @return (∀p ∈ obj : ∃(w ∈ words : postContainword(p,w)))) 
	 * @throws NullPointerException if words is null
	 * @throws TextOverflowException 
	 */
	public List<Post> containing(List<String> words) throws NullPointerException, TextOverflowException;
		
	/**
	 * Restituisce una mappa di utenti come chiavi e chi essi seguono come valori, in base a chi sono gli autori 
	 * della list dei post che sono passati come parametro. 
	 * @param ps 
	 * @requires ps != null ∧ ∀(p ∈ ps : p != null)
	 * @effects true
	 * @modifies null 
	 * @return (∀u ∈ keysof(obj) : ∃(p ∈ ps : u ∈ isauthor(p)))
	 * @throws NullPointerException if ps is null
	 */
	public Map<String,Set<String>> guessFollowers(List<Post> ps) throws NullPointerException;

	/**
	 * Restituisce le informazioni del post formattate recuperato dall'id dato come parametro.
	 * @param id 
	 * @requires id >= 0
	 * @effects true
	 * @modifies null 
	 * @throws PostNotFoundException if Post not found
	 * @throws IllegalArgumentException if id < 0 
	 */
	public void PublishPostById(int id) throws PostNotFoundException,IllegalArgumentException;
}

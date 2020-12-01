package interfaces;

import java.util.Set;

import exceptions.LikeNotAllowedExeption;
import exceptions.PostNotFoundException;
import exceptions.TextOverflowException;
import exceptions.UserNotFoundException;

public interface Populable {

	/**
	 *
	 * UTENTI {<utente_1>...<utente_n>}, un insieme modificabile di utenti.
	 * 
	 * POSTS  {<autore1,{post1..postn}>...<autore2,{post1..postn}>},
	 *  		una mappa che associa un autore ad una serie di post.
	 * 
	 * FOLLOWER(u), l'insieme modifiabile di utenti seguiti da un l'utente u.
	 */
	
	
	/** 
	 * Aggiunge l'Utente con il nome "user" nel socialnetwork se non è presente inizializzando l'insieme
	 * dei seguaci come un insieme vuoto.
	 * @param user
	 * @requires user != null ∧ user.lenght != 0
	 * @effects !(user ∈ UTENTI) => (UTENTI = UTENTI U user) ∧ follower(u) is empty
								else UTENTI ∧ follower(u) they aren't changed.
	 * @modifies UTENTI ∧ FOLLOWER(u)
	 * @return true if user was added, false if user is already present.
	 * @throws NullPointerException if user is null
	 * @throws IllegalArgumentException if user is "empty" (user = "")
	 */
	public boolean signup(String user) throws NullPointerException,IllegalArgumentException;
	
	
	/**
	 * Aggiunge l'utente ed i suoi follower nel socialnetwork se non è presente,
	 * altimenti aggiorna la sua cerchia dei follower ripiazzandola con il nuovo insieme.
	 * @param user
	 * @param follower
	 * @requires user != null ∧ user.lenght != 0 ∧ follower != null ∧ 
	 * 			 (∀ u ∈ follower : u!=null ∧ u!="" ∧ u ∈ UTENTI-user )
	 * @effects (user ∈ UTENTI) =>  follower(user) = follower 
	 * 				           else (UTENTI = UTENTI U user) ∧ follower(user) = follower 
	 * @modifies UTENTI ∧ follower(u)
	 * @return true if add user , false if alredy present
	 * @throws NullPointerException if user is null or follower is null
	 * @throws IllegalArgumentException if user is "empty" (user = "")
	 * @throws UserNotFoundException if a element of follower is not user into network
	 */
	public boolean signup(String user,Set<String> follower) 
			throws NullPointerException,IllegalArgumentException,UserNotFoundException;
	
	/**
	 * Aggunge i followers nell'insieme dei seguaci dell'utente "user".
	 * Se un follower è già presente lo ignora.
	 * @param user
	 * @param follower
	 * @requires user != null ∧ user ∈ UTENTI ∧
	 *  		follower != null ∧ (∀u ∈ follower : u!=null ∧ u!="" ∧ u ∈ UTENTI-user )
	 * @effects (∀f ∈ follower :  !(f ∈ follower(user)) => follower(user) = follower(user) U f )
	 * @modifies follower(user)
	 * @return true
	 * @throws NullPointerException if user is null or follower is null
	 * @throws IllegalArgumentException if a element of follower is "empty" (follower_i = "")
	 * @throws UserNotFoundException if a element of follower is not user into network
	 */
	public boolean follow(String user,Set<String> follower) 
			throws NullPointerException,IllegalArgumentException,UserNotFoundException;
	
	/**
	 * Aggiunge un post nella rete sociale se non è presente.
	 * Il post conterrà anche un id gestito dal social e la data di pubblicazione del post.
	 * Si possono taggare anche le persone che non si seguono. 
	 * @param author
	 * @param text
	 * @param tags
	 * @requires 	 author != null ∧ author ∈ UTENTI ∧
	 * 				   text != null ∧ text.lenght != 0  ∧ text.lenght <= 140  ∧
	 * 				   tags != null ∧ (∀t_i ∈ tags : t_i!= null ∧ t_i ∈ UTENTI) ∧
	 * @effects POSTS = POSTS U p_x ∧ (∀p_i ∈ Posts : p_x != p_i) (deep equals) 
	 * @modifies POSTS
	 * @return return true if add Post otherwise false
	 * @throws NullPointerException if author is null or text is null or tags is null
	 * @throws UserNotFoundException if author or tags(author mentioned) are not presenent into Social
	 * @throws IllegalArgumentException if user is "empty" (user = "")
	 * @throws TextOverflowException if text.lenght > 140 
	 */
	public boolean post(String author, String text,Set<String> tags) 
			throws NullPointerException,UserNotFoundException,IllegalArgumentException,TextOverflowException;
	
	/**
	 * Aggiunge un like al post con id dato dal parametro, da un utente "user"
	 * l'utente che mette like deve seguire l'autore del post con l'id "id".
	 * @param user
	 * @param id
	 * @definition IDPOST("post","id") restituisce true se il post ha come identificativo "id".
	 * 	           POSTBY("id") restituisce il post da l'id.
	 * 		   	   FOLLOWERBYPOST("post") restuisce l'insieme dei follower dall'autore del post.
	 * 
	 * @requires  user!= null ∧ id >= 0 ∧ (user ∈ UTENTI) ∧ (∀p in POSTS : ∃! IDPOST(p,id)) ∧
	 * 		      (user ∈ FOLLOWERBYPOST(POSTBY("id"))) 
	 * @return true if like was added. false otherwise
	 * @modifies post(i) ∈ POSTS
	 * @throws NullPointerException if user == null
	 * @throws IllegalArgumentException if "id" < 0
	 * @throws UserNotFoundException if !∃ user with username = "uset"
	 * @throws PostNotFoundException if !∃ post with identification = "id"
	 * @throws LikeNotAllowedExeption if like wasn't permitted
	 */
	public boolean like(String user,int id) 
			throws NullPointerException,IllegalArgumentException,UserNotFoundException,
				   PostNotFoundException,LikeNotAllowedExeption;
	
	
	public void debugAllUser();
	public void debugAllPost();
}

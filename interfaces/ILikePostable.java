package interfaces;

import java.util.Set;

import exceptions.LikeNotAllowedExeption;

public interface ILikePostable {
	
	/*
	 * Overview: LikedPost è un estenzione di Post la quale contiene un insieme modificabile
	 * 			 di utenti iscritti al social che hanno avuto una reazione al Post.
	 * 
	 * Typical Element: LIKES = {<utente_1>...<utente_n>}, l'insieme degli untenti che hanno messo like.
	 */
	
	
	/**
	 * Aggiunge l'utente all' insieme degli utenti che abbiano messo like,
	 * se è già presente lo ignora,non si può mettere like ad un proprio post.
	 * @param user
	 * @requires user != null ∧ user != author
 	 * @effects  !(user ∈ LIKES) => LIKES = LIKES U user 
 	 * @modifies this
	 * @return true if user was added, false otherwise
	 * @throws LikeNotAllowedExeption if user = author
	 */
	public boolean addLike(String user) throws LikeNotAllowedExeption;
	
	
	/**
	 * Restituisce l'insieme degli utenti che hanno messo like.
	 * @requires true
	 * @effects true
	 * @modifies null
	 * @return  value != null 
	 */
	public Set<String> getLike();
	
	/**
	 * Restituisce il numero di utenti che hanno messo like.
	 * @Precondition true
	 * @effects true
	 * @modifies null
	 * @return value >=0
	 */
	public int getNumLike();
	
}

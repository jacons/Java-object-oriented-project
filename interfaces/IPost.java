package interfaces;

import java.util.Set;

import exceptions.TextOverflowException;

public interface IPost {
	
    /*
     * Overview: Post rappresenta un oggetto immutable con : un id univoco, un autore,
     * 			 un testo di massimo 140 caratteri e la data di creazione.
     * 
     * Typical Element : <id,author,text,timestamp> dove author e text sono passati dal
     * 				     costuttore.
     */
	
	
	/**
	 * Restituisce la lunghezza del testo del post.
	 * @requires true
	 * @effects true
	 * @modifies null
	 * @return 0 < value < 140 chars
	 */
	public int getLenghtText();
	
	/**
	 * Restituisce l'autore del post.
	 * @requires true
	 * @effects true
	 * @modifies null
	 * @return value != null ∧ value.lenght != 0
	 */
	public String getAuthor();
	
	/**
	 * Restuisce l'identificativo del post.
	 * @requires true
	 * @effects true
	 * @modifies null
	 * @return value >= 0 
	 */
	public int getId();
	
	/**
	 * Restituisce il testo del post.
	 * @requires true
	 * @effects true 
	 * @modifies null
	 * @return text != null
	 */
	public String getText();
	
	/**
	 * Restituisce una lista immutabile che rappresenta l'insieme delle persone 
	 * che sono state taggate del post.
	 * @requires true
	 * @effects true 
	 * @modifies null
	 * @return (∀s1 ∈ values : s1!=null)
	 */	
	public Set<String> getMenzioned();
	
	/**
	 * Restituisce l'array di parole con cui è composto il testo del post
	 * ordinate in modo crescente.
	 * @requires true
	 * @effects true
	 * @modifies null
	 * @return un array ordinato in modo non decrescente. 
	 */
	public String[] sortedexplodetext();
	
	
	/**
	 * Restituisce true se l'oggetto passato come parameto ha lo stesso autore
	 * e lo stesso testo del post.
	 * @param o
	 * @requires true
	 * @effects true
	 * @modifies null
	 * @return ( author(o) = author || text(o)= text ) => true.  false otherwise
	 */
	public boolean equals(Object o);
	
	
	/**
	 * Restutuisce un nuovo oggetto uguale al del post.
	 * @requires true
	 * @effects true
	 * @modifies null
	 * @return  value != null ∧ this.equals(value) 
	 * @throws TextOverflowException 
	 */
	public Object MyClone() throws TextOverflowException;

	
}

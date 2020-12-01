package NoOffensiveInterfaces;

import java.util.Date;

public interface IReport {
	
	/* 
	 * 
	 * Overview: Report rappresenta un report, un insieme di informazioni immutabili 
	 * 			 da allegare ad un post per segnararlo di contentenuti offensivi.
	 * 
	 * Typical Element : Un insieme di informazioni che caratterizzano una segnalazione.
	 */
	
	/**
	* Restituisce la data di quando è stata fatta la segnalazione.
	* @requires true
	* @effects true
	* @modifies null 
	* @return Date != null
	*/
	public Date getData();

	/**
	* Restituisce il nome del "utente" del Social Network che ha effettuato la segnalazione.
	* @requires true
	* @effects true
	* @modifies null 
	* @return Stirng != null
	*/
	public String getSupervisor();

	/**
	* Restituisce il motivo della segnalazione.
	* @requires true
	* @effects true
	* @modifies null 
	* @return String != null
	*/
	public String getComment();

}

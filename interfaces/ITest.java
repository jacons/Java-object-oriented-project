package interfaces;

import NoOffensiveInterfaces.INoOffensiveSocial;

public interface ITest {
	
	/**
	 * Funzione che popola con dati il social network al fine di fare testing.
	 * @param sn 
	 * @requires sn != null
	 * @effects true
	 * @modifies null
	 * @throws NullPointerException
	 */
	public void Populate(Populable sn) throws NullPointerException;
	
	/**
	 * Funzione che utilizza i metodi dati nella speficia di ISocial  al fine di fare testing.
	 * @param sn 
	 * @requires sn != null
	 * @effects true
	 * @modifies null
	 * @throws NullPointerException
	 */
	public void Analyzed(ISocial sn) throws NullPointerException;
	
	/**
	 * Funzione che utilizza i metodi dati nella speficia di INoOffensiveSocial  al fine di fare testing.
	 * @param sn 
	 * @requires sn != null
	 * @effects true
	 * @modifies null
	 * @throws NullPointerException
	 */
	public void NoOffensive(INoOffensiveSocial sn) throws NullPointerException;
}

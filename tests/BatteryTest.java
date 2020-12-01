package tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import NoOffensiveInterfaces.INoOffensiveSocial;
import NoOffensiveMicroBlog.NoOffensiveSocial;
import interfaces.ISocial;
import interfaces.Populable;
import interfaces.ITest;
import microblog.Post;
import microblog.Social;

public class BatteryTest implements ITest {

	
	@Override
	public void Populate(Populable sn) {
	
		// Creo un utenti
		System.out.println("Iscrizione effettuata? "+sn.signup("Marcello"));
		System.out.println("Iscrizione effettuata? "+sn.signup("Filippo"));
		System.out.println("Iscrizione effettuata? "+sn.signup("Beatrice"));
		System.out.println("Iscrizione effettuata? "+sn.signup("Luigi"));
		System.out.println("Iscrizione effettuata? "+sn.signup("Pino"));
		System.out.println("Iscrizione effettuata? "+sn.signup("Gina"));
		System.out.println("Iscrizione effettuata? "+sn.signup("Patrizio"));
		System.out.println("Iscrizione effettuata? "+sn.signup("Alessio"));
		
		System.out.println("Iscrizione effettuata? "+sn.signup("Luigi")+" (già iscritto)");

		// eccezione generata con campi nulli
		try {
			System.out.println("Iscrizione effettuata? "+sn.signup(null));			
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
		}
		
		// eccezione generata con campi vuoti
		try {
			System.out.println("Iscrizione effettuata? "+sn.signup(""));			
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
		}
		System.out.println("-----------");
		
		
		//Aggiungo follower e altri utenti con già followers nel costruttore testando
		//i casi in cui siano null e utenti sconosciuti
		
		try {
			HashSet<String> list = new  HashSet<String>();
			list.add("Marcello");list.add("Filippo");list.add("Beatrice");
			System.out.println("Follower aggiunti? "+sn.follow("Luigi",list));

			list.clear();
			list.add("Alessio");list.add("Gina");list.add("Luigi");list.add("Beatrice");
			System.out.println("Follower aggiunti? "+sn.follow("Marcello",list));			

			list.clear();
			list.add("Pino");
			System.out.println("Follower aggiunti? "+sn.follow("Beatrice",list));			

			list.clear();
			list.add("Alessio");list.add("Gina");
			System.out.println("Follower aggiunti? "+sn.follow("Filippo",list));			

			
			list.clear();
			list.add("Gina");
			System.out.println("Follower aggiunti? "+sn.follow("Patrizio",list));			

			list.clear();
			list.add("Filippo");
			System.out.println("Follower aggiunti? "+sn.follow("Gina",list));			

			
			list.clear();
			list.add("Beatrice");
			System.out.println("Follower aggiunti? "+sn.follow("Alessio",list));			

			
		} catch (Exception e) { e.printStackTrace(); }
		
		// eccezione generata con campi nulli
		try {		
			System.out.println("Follower aggiunti? "+sn.follow("Marcello",null));
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
			System.out.println("-----------");
		}
		
		
		
		HashSet<String> list = new HashSet<String>();
		
		try {
			System.out.println("Post aggiunto? "+sn.post("Alessio","Oggi è una bella giornata",new HashSet<String>()));
			
			System.out.println("Post aggiunto? "+sn.post("Beatrice","Il gatto mi ha graffiata!",new HashSet<String>()));

			list.add("Patrizio");list.add("Pino");list.add("Luigi");	
			System.out.println("Post aggiunto? "+sn.post("Filippo","Che bello,domani vacanza con i miei amici", list));

			list.clear();list.add("Marcello");
			System.out.println("Post aggiunto? "+sn.post("Filippo","Sono molto arrabbiato con te Marcello",list));
			
			System.out.println("Post aggiunto? "+sn.post("Filippo","Sono molto arrabbiato con te Marcello",list));
			
			System.out.println("Post aggiunto? "+sn.post("Beatrice","Secondo me Trump sta esagerando con queste elezioni",new HashSet<String>()));
			
			System.out.println("Post aggiunto? "+sn.post("Gina","Quest'anno mi sono impegnato molto a scuola",new HashSet<String>()));
						
			list.clear();list.add("Alessio");
			System.out.println("Post aggiunto? "+sn.post("Beatrice","La pioggia ci mette sempre di cattivo umore,Alessio", list));

			list.clear();list.add("Alessio");
			System.out.println("Post aggiunto? "+sn.post("Beatrice","La pioggia ci mette sempre di cattivo umore,Alessio", list));


		} catch (Exception e) { e.printStackTrace(); }
		
		
	
		// eccezione generata dal valore null
		try {
			System.out.println("Post aggiunto? "+sn.post("Pino","La matematica è molto affascinate",null));
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
		}
				
		// eccezione generata al un utente inesistente
		try {
			System.out.println("Post aggiunto? "+sn.post("Marcella","",new HashSet<String>()));
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
		}		
		// ecezione generata da un tag inesistente
		try {
			list.clear();list.add("Samuele");
			System.out.println("Post aggiunto? "+sn.post("Alessio","Samuele, ti ricordi il nostro ultimo incotro?",list));
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
		}

		//eccezione generata con testo vuoto
		try {
			System.out.println("Post aggiunto? "+sn.post("Pino","",new HashSet<String>()));
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
		}
		
		// ecezione generata per testo lungo
		try {
			System.out.println("Post aggiunto? "+sn.post("Pino","Oggi è passata in radio la mia canzione preferita"
					+ "sono felice,peccato che non sapevo le parole,e sono stato zitto"
					+ "pero mi sono goduto il viaggio",new HashSet<String>()));
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
			System.out.println("-----------");
		}

		
        try {
        	System.out.println("Like aggiunto? "+sn.like("Luigi"    ,4));
        	System.out.println("Like aggiunto? "+sn.like("Luigi"    ,1));
        	System.out.println("Like aggiunto? "+sn.like("Luigi"    ,6));
        	System.out.println("Like aggiunto? "+sn.like("Patrizio" ,5));
        	System.out.println("Like aggiunto? "+sn.like("Alessio"  ,4));
        	System.out.println("Like aggiunto? "+sn.like("Marcello" ,4));
        	System.out.println("Like aggiunto? "+sn.like("Gina"     ,3));
        	System.out.println("Like aggiunto? "+sn.like("Marcello" ,1));
		} catch (Exception e) {
			System.out.println("ERRORE -- "+e.getMessage());
		}
        
		sn.debugAllPost();		

		//eccezione generata con id < 0
		try {
        	System.out.println("Like aggiunto? "+sn.like("Marcello" ,-1));
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
		}
		
		// eccezione generata con id inesistente
		try {
        	System.out.println("Like aggiunto? "+sn.like("Marcello" ,45));
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
		}
		// eccezione generata con user nullo
		try {
        	System.out.println("Like aggiunto? "+sn.like(null ,45));
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
		}	
		// eccezione generata con user inesistente
		try {
        	System.out.println("Like aggiunto? "+sn.like("Marcella" ,4));
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
		}
		// eccezione generata quando un utente mette like ad un post di un autore che non segue
		try {
        	System.out.println("Like aggiunto? "+sn.like("Filippo" ,4));
		} catch (Exception e) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
			System.out.println("-----------");
		}
		sn.debugAllUser();
		
	}
	@Override
	public void Analyzed(ISocial sn) {
		
		System.out.println("------MICROBLOG ANALYSIS-------");
		
		List<String> simplewords= new ArrayList<String>(3);
		simplewords.add("giornata");simplewords.add("gatto");
		simplewords.add("vacanza"); simplewords.add("amici");
	
		try {
			System.out.println("words ricercate: "+simplewords);
			List<Post> filteredpost = sn.containing(simplewords);
			
			System.out.print("\nGli id dei post che sono stati recuperati: ");
			for(Post p : filteredpost) System.out.print(p.getId()+" ");
			System.out.println("\n");
			
			System.out.print("Gli id dei post che sono stati scritti da Filippo dal set di post\nrecuperai con contains : ");
			List<Post> filteredpost2 = Social.writtenBy(filteredpost,"Filippo");
			for(Post p : filteredpost2) System.out.print(p.getId()+" ");
			System.out.println();
			
		} catch (Exception e) { e.printStackTrace(); }

		
		// eccezione generata da un elemento nullo nella list 
		try {
			simplewords.clear();
			simplewords.add("arrabiato");simplewords.add(null);
			sn.containing(simplewords);
		} catch(Exception e ) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
			System.out.println("-----------");

		}
		
		System.out.print("\nI nomi degli utenti che sono stati menzionati almeno una volta:\n");
		System.out.println(sn.getMentionedUsers());
		

		System.out.print("\nI nomi degli utenti che sono seguiti di più (maggiori a 0):\n");		
		System.out.println(sn.influencers());

		
		try {
			System.out.print("\nGli id dei post scritti da un determinato autore: ");
			List<Post> authorpost = sn.writtenBy("Filippo");
			for(Post p : authorpost) System.out.print(p.getId()+" ");
			System.out.println("\n");
			
			System.out.print("Il set degli utenti menzionati da un insieme di post\nrecuperati dalla written by(Filippo) : ");
			System.out.println(Social.getMentionedUsers(authorpost));

		} catch (Exception e) { e.printStackTrace();}
		
		// Eccezione generata da un utente nullo 
		try {
			sn.writtenBy(null);
		} catch(Exception e ) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
			System.out.println("-----------");
		}	
		// Eccezione generata da un utente inesistentes
		try {
			sn.writtenBy("Marcella");
		} catch(Exception e ) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
			System.out.println("-----------");
		}			
		
		
		List<Post> posts = null;
		try {
			// utilizzo containts per recuperare una list di post da utilizzare nel testing
			simplewords.clear();simplewords.add("con");
			posts = sn.containing(simplewords);
		} catch (Exception e) {e.printStackTrace();}

		System.out.println("");
		Map<String,Set<String>> result = sn.guessFollowers(posts);
		System.out.println(result);
	}	
	
	public void NoOffensive(INoOffensiveSocial sn) throws NullPointerException {

		System.out.println("----NO OFFENSIVE SOCIAL----");
		try {
			System.out.println("\nAggiunto supervisore? : "+sn.Supervisor("Alessio"));
		} catch (Exception e) { e.printStackTrace(); }

		// eccezione generata con utente inesistente
		try {
			System.out.println("\nAggiunto supervisore? : "+sn.Supervisor("Marcella"));
		} catch(Exception e ) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
			System.out.println("-----------");
		}
		
		// segnalo un post presente nella rete
		try {
			sn.Report("Alessio",4,"Political Opinion");
		} catch (Exception e) {e.printStackTrace();}
		
		
		//eccezione generata da post insistente
		try {
			sn.Report("Alessio",440,"ciao");
		} catch(Exception e ) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
			System.out.println("-----------");
		}
		
		//eccezione generata da utente che non sia supervisore
		try {
			sn.Report("Gina",4,"ciao");
		} catch(Exception e ) {
			System.out.println("-----------");
			System.out.println("ERRORE -- "+e.getMessage());
			System.out.println("-----------");
		}
		
		// post non segnalato
		try {
			sn.PublishPostById(3);
		} catch (Exception e) {e.printStackTrace();}

		// post non segnalato con commento inerente
		try {
			sn.PublishPostById(4);
		} catch (Exception e) {e.printStackTrace();}

		
		
	}	
	public BatteryTest() {

		NoOffensiveSocial social = new NoOffensiveSocial();
		
		// inserisce dati per testing
		Populate(social);
		
		// utilizza i metodi definiti dalla specifica del progetto
		Analyzed(social);
		
		// utilizza i metodi della classe estesa,preservando il principio di sostituzione
		NoOffensive(social);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		BatteryTest test = new BatteryTest();
		
	}
		
}

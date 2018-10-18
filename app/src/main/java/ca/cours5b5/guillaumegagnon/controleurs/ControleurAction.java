package ca.cours5b5.guillaumegagnon.controleurs;

import java.util.List;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.global.GCommande;

public class ControleurAction {

    private static Map<GCommande, Action> actions;
    private static List<Action> fileAttenteExecution;

    static {
        for (GCommande commande : GCommande.values()
             ) {
                actions.put(commande, new Action());
        }
    }

    public static Action demanderAction(GCommande commande){


        return actions.get(commande);
    }

    public static void fournirAction(Fournisseur fournisseur, GCommande commande, ListenerFournisseur listenerFournisseur){

    }
    static void executerDesQuePossible(Action action){

    }

    private static void executerActionsExecutables(){

    }

    static boolean siActionExecutable(Action action){
        boolean exe = false;

        if(action.listenerFournisseur != null)
            exe = true;

        return exe;
    }

    private static synchronized void executerMaintenant(Action action){

    }

    private static void lancerObservationSiApplicable(Action action){

    }

    private static void enregistrerFournisseur(Fournisseur fournisseur, GCommande commande, ListenerFournisseur listenerFournisseur){

    }

    private static void ajouterActionEnFileDAttente(Action action){

    }
}

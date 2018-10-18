package ca.cours5b5.guillaumegagnon.controleurs;

import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.modeles.Modele;

public class ControleurAction {

    private static Map<GCommande, Action> actions;
    private static Set<Action> fileAttenteExecution;

    static {
        actions = new HashMap<>();
        fileAttenteExecution = new HashSet<>();
        for (GCommande commande: GCommande.values()) {
            actions.put(commande, new Action());
        }
    }

    public static Action demanderAction(GCommande commande){
        Action action = null;
        for(Map.Entry<GCommande, Action> entry : actions.entrySet()){
            if(entry.getKey().equals(commande)){
                action = entry.getValue();
            }
        }
        return action;
    }

    public static void fournirAction(Fournisseur fournisseur, GCommande commande, ListenerFournisseur listenerFournisseur){
        enregistrerFournisseur(fournisseur, commande, listenerFournisseur);
        executerActionsExecutables();
    }
    static void executerDesQuePossible(Action action){
        Log.d("Atelier07", ControleurAction.class.getSimpleName() +".executerDesQuePossibles");
        ajouterActionEnFileDAttente(action);
        executerActionsExecutables();
    }

    private static void executerActionsExecutables(){
        Log.d("Atelier07", ControleurAction.class.getSimpleName() +".executerActionsExecutables");
        for(Action action : fileAttenteExecution) {
            if(siActionExecutable(action)){
                fileAttenteExecution.remove(action);
                executerMaintenant(action);
                lancerObservationSiApplicable(action);
            }
        }
    }

    static boolean siActionExecutable(Action action){
        boolean executable = false;
        if(action.listenerFournisseur != null){
            executable = true;
        }
        return executable;
    }

    private static synchronized void executerMaintenant(Action action){
        Log.d("Atelier07", ControleurAction.class.getSimpleName() + ".executerMaintenant");
        action.listenerFournisseur.executer(action.args);
    }

    private static void lancerObservationSiApplicable(Action action){
        Log.d("Atelier07", ControleurAction.class.getSimpleName() +".lancerObservationSiApplicable");
        if(action.fournisseur instanceof Modele){
            ControleurObservation.lancerObservation((Modele)action.fournisseur);
        }
    }

    private static void enregistrerFournisseur(Fournisseur fournisseur, GCommande commande, ListenerFournisseur listenerFournisseur){
        Action action = actions.get(commande);
        action.fournisseur = fournisseur;
        action.listenerFournisseur = listenerFournisseur;
    }

    private static void ajouterActionEnFileDAttente(Action action){
        fileAttenteExecution.add(action);
    }
}

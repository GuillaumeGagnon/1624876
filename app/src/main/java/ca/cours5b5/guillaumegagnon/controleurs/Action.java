package ca.cours5b5.guillaumegagnon.controleurs;

import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;

public class Action {
    Fournisseur fournisseur;

    ListenerFournisseur listenerFournisseur;

    Object[] args;

    public void setArguments(Object... args){
        this.args = args;
    }

    public void executerDesQuePossible(){
        //ControleurAction
        ControleurAction.executerDesQuePossible(this);
    }

    Action cloner(){
        Action action = new Action();

        action.fournisseur = fournisseur;
        action.listenerFournisseur = listenerFournisseur;


        if(!args.equals(null)){
            action.args = args.clone();
        }

        return action;
    }
}

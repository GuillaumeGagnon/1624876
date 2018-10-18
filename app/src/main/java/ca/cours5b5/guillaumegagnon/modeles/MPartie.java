package ca.cours5b5.guillaumegagnon.modeles;

import java.util.Map;

import ca.cours5b5.guillaumegagnon.exceptions.ErreurSerialisation;
import ca.cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MPartie extends Modele{


    @AttributSerialisable
    public MParametresPartie parametres;
    private final String __parametres = "parametres";

    public MPartie(MParametresPartie parametres){
        this.parametres = parametres;
    }

    public MParametresPartie getParametres(){
        return parametres;
    }


    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation {
        /*unused for now*/
    }

    @Override
    public Map<String, Object> enObjetJson() throws ErreurSerialisation {
        /*unused for now*/
        return null;
    }
}

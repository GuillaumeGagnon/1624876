package ca.cours5b5.guillaumegagnon.donnees;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.global.GConstantes;
import ca.cours5b5.guillaumegagnon.serialisation.Jsonification;

public final class Disque extends SourceDeDonnees {

    private Disque(){}

    private static final Disque instance = new Disque();

    public static Disque getInstance() {
        return instance;
    }

    private File repertoireRacine;


    public void setRepertoireRacine(File repertoireRacine) {
        this.repertoireRacine = repertoireRacine;
    }


    @Override
    public void chargerModele(String cheminSauvegarde, ListenerChargement listenerChargement) {

        File fichier = getFichier(cheminSauvegarde);

        try {

            String json = new String(Files.readAllBytes(fichier.toPath()));

            Map<String, Object> objetJson = Jsonification.aPartirChaineJson(json);

            listenerChargement.reagirSucces(objetJson);

        } catch (IOException e) {

            listenerChargement.reagirErreur(e);

        }
    }


    @Override
    public void sauvegarderModele(String cheminSauvegarde, Map<String, Object> objetJson) {

        File fichier = getFichier(cheminSauvegarde);

        String json = Jsonification.enChaineJson(objetJson);

        try {

            OutputStream outputStream = new FileOutputStream(fichier);

            outputStream.write(json.getBytes());

            outputStream.close();

        } catch (FileNotFoundException e) {

            Log.d("Atelier07", "File not found: " + cheminSauvegarde);

        } catch (IOException e) {


            Log.d("Atelier07", "IOException: " + cheminSauvegarde);

        }
    }


    @Override
    public void detruireSauvegarde(String cheminSauvegarde) {

        File fichier = getFichier(cheminSauvegarde);
        fichier.delete();

    }


    private File getFichier(String cheminSauvegarde) {

        String nomModele = getNomModele(cheminSauvegarde);

        String nomFichier = getNomFichier(nomModele);

        return new File(repertoireRacine, nomFichier);

    }


    private String getNomFichier(String nomModele) {

        return nomModele + GConstantes.EXTENSION_PAR_DEFAUT;

    }


}
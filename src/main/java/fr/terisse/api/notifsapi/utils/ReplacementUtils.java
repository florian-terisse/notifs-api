package fr.terisse.api.notifsapi.utils;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ReplacementUtils {

    final Map<String, String> replacements = new HashMap<>();

    static {
        replacements.put("COND VSAV", "conducteur VSAV");
        replacements.put("COND FPT", "conducteur FPT");
        replacements.put("CA VSAV", "chef d'agrès VSAV");
        replacements.put("CA FPT", "chef d'agrès VSAV");
        replacements.put("CE FPT", "chef d'équipe FPT");
        replacements.put("Depart", "Départ");
        replacements.put("TERISSE", "TÉRISSE");
        replacements.put("SAP REG", "Secours à personne - Régulation -");
        replacements.put("FEU D HABITATION INDIVIDUELLE", "Feu d'habitation individuelle");
    }

    public String makeReplacements(String message) {
        String lReturn = message;
        for(Map.Entry<String, String> replacement : replacements.entrySet()) {
            lReturn = lReturn.replaceAll(replacement.getKey(), replacement.getValue());
        }
        return lReturn;
    }


}

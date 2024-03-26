package fr.terisse.api.notifsapi.utils;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ReplacementUtils {

    final Map<String, String> replacements = new HashMap<>();

    static {
        replacements.put("COND VSAV", "conducteur VSAV");
        replacements.put("CA VSAV", "chef d'agrès VSAV");
        replacements.put("Depart", "Départ");
        replacements.put("TERISSE", "TÉRISSE");
        replacements.put("SAP REG", "Secours à personne - Régulation -");
    }

    public String makeReplacements(String message) {
        String lReturn = message;
        for(Map.Entry<String, String> replacement : replacements.entrySet()) {
            lReturn = lReturn.replaceAll(replacement.getKey(), replacement.getValue());
        }
        return lReturn;
    }


}

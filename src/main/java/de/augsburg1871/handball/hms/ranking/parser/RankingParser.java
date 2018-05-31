package de.augsburg1871.handball.hms.ranking.parser;

import com.google.common.collect.Maps;
import de.augsburg1871.handball.hms.ranking.parser.model.NoteTO;
import de.augsburg1871.handball.hms.ranking.parser.model.RankTO;
import de.augsburg1871.handball.hms.ranking.parser.model.RankingTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

import static de.augsburg1871.handball.hms.ranking.parser.ValueExtractor.getAsInt;
import static de.augsburg1871.handball.hms.ranking.parser.ValueExtractor.getAsString;


public class RankingParser {

    private static final String RANG = "Rang";
    private static final String MANNSCHAFT = "Mannschaft";
    private static final String BEGEGNUNGEN = "Begegnungen";
    private static final String SIEGE = "S";
    private static final String UNENTSCHIEDEN = "U";
    private static final String NIEDERLAGEN = "N";
    private static final String TORE = "Tore";
    private static final String TOR_DIFFERENZ = "+/-";
    private static final String PUNKTE = "Punkte";

    public static RankingTO parse(final Element tabelle) {
        final Map<String, Integer> indicies = Maps.newHashMap();
        final Elements rows = tabelle.getElementsByTag("tr");
        final Elements headline = rows.first().getElementsByTag("th");

        // Zuordnung von Index zu Überschrift
        for (int i = 0; i < headline.size(); i++) {
            indicies.put(headline.get(i).text(), i);
        }

        RankingTO ranking = new RankingTO();
        // zeilenweise lesen
        for (int i = 1; i < rows.size(); i++) {
            // spaltenweise lesen
            final Elements columns = rows.get(i).getElementsByTag("td");

            // nichts hinzufügen, wenn "Pattern" nicht matched
            if (columns.size() != 5 && columns.size() != 10) {
                continue;
            }

            // wenn wir zehn spalten haben gehen wir von einem regulären eintrag aus
            if (columns.size() == 10) {
                final RankTO rank = new RankTO();
                rank.setRang(getAsInt(columns.get(indicies.get(RANG)).text()));
                rank.setMannschaft(getAsString(columns.get(indicies.get(MANNSCHAFT)).text()));
                rank.setBegegnungen(getAsInt(columns.get(indicies.get(BEGEGNUNGEN)).text()));
                rank.setSiege(getAsInt(columns.get(indicies.get(SIEGE)).text()));
                rank.setUnentschieden(getAsInt(columns.get(indicies.get(UNENTSCHIEDEN)).text()));
                rank.setNiederlagen(getAsInt(columns.get(indicies.get(NIEDERLAGEN)).text()));
                rank.setTore(getAsString(columns.get(indicies.get(TORE)).text()));
                rank.setTorDifferenz(getAsString(columns.get(indicies.get(TOR_DIFFERENZ)).text()));
                rank.setPunkte(getAsString(columns.get(indicies.get(PUNKTE)).text()));
                ranking.addRank(rank);
            }

            // wenn wir fünf spalten haben versuchen wir den hinweis zu extrahieren
            if (columns.size() == 5) {
                NoteTO note = new NoteTO();
                note.setRang(getAsInt(columns.get(1).text()));
                note.setMannschaft(getAsString(columns.get(2).text()));
                note.setHinweis(getAsString(columns.get(4).text()));
                ranking.addNote(note);
            }

        }

        return ranking;
    }

}

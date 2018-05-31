package de.augsburg1871.handball.hms.ranking.parser;

import de.augsburg1871.handball.hms.ranking.crawler.RankingCrawler;
import de.augsburg1871.handball.hms.ranking.crawler.RankingNotFoundException;
import de.augsburg1871.handball.hms.ranking.parser.model.NoteTO;
import de.augsburg1871.handball.hms.ranking.parser.model.RankTO;
import de.augsburg1871.handball.hms.ranking.parser.model.RankingTO;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class RankingParserTest {

    private final ClassPathResource html = new ClassPathResource("tabelle-maenner2.html");
    private final RankingCrawler crawler = new RankingCrawler();

    @Test
    public void parse() throws IOException, RankingNotFoundException {
        Element element = crawler.readFrom(html.getInputStream(), UTF_8, html.getURI().toASCIIString());
        RankingTO ranking = RankingParser.parse(element);

        // Tabelle hat 10 gewertete Mannschaften ...
        assertThat(ranking.getRankTOs()).hasSize(10);
        // ... und einen Hinweis
        assertThat(ranking.getNoteTOs()).hasSize(1);

        // Alle Felder vom Ersten und Letzten prüfen
        RankTO rank = ranking.getRankTOs().first();
        assertThat(rank.getRang()).isEqualTo(1);
        assertThat(rank.getMannschaft()).isEqualTo("TSV Friedberg IV");
        assertThat(rank.getBegegnungen()).isEqualTo(18);
        assertThat(rank.getSiege()).isEqualTo(15);
        assertThat(rank.getUnentschieden()).isEqualTo(1);
        assertThat(rank.getNiederlagen()).isEqualTo(2);
        assertThat(rank.getTore()).isEqualTo("565:323");
        assertThat(rank.getTorDifferenz()).isEqualTo("+242");
        assertThat(rank.getPunkte()).isEqualTo("31:5");

        RankTO last = ranking.getRankTOs().last();
        assertThat(last.getRang()).isEqualTo(10);
        assertThat(last.getMannschaft()).isEqualTo("PSV Aug.");
        assertThat(last.getBegegnungen()).isEqualTo(18);
        assertThat(last.getSiege()).isEqualTo(1);
        assertThat(last.getUnentschieden()).isEqualTo(1);
        assertThat(last.getNiederlagen()).isEqualTo(16);
        assertThat(last.getTore()).isEqualTo("365:581");
        assertThat(last.getTorDifferenz()).isEqualTo("-216");
        assertThat(last.getPunkte()).isEqualTo("3:33");

        // Felder vom Hinweis prüfen
        NoteTO note = ranking.getNoteTOs().iterator().next();
        assertThat(note.getRang()).isEqualTo(11);
        assertThat(note.getMannschaft()).isEqualTo("SV Mering II");
        assertThat(note.getHinweis()).isEqualTo("zurückgezogen am 10.11.2017");
    }

}
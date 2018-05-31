package de.augsburg1871.handball.hms.ranking.crawler;

import org.jsoup.nodes.Element;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class RankingCrawlerTest {

    private final ClassPathResource html = new ClassPathResource("tabelle-maenner2.html");
    private final RankingCrawler crawler = new RankingCrawler();

    @Test
    public void readTable() throws IOException, RankingNotFoundException {
        Element element = crawler.readFrom(html.getInputStream(), UTF_8, html.getURI().toASCIIString());
        assertThat(element.tagName()).isEqualTo("table");
        assertThat(element.getElementsByTag("tr")).hasSize(12);
    }

}
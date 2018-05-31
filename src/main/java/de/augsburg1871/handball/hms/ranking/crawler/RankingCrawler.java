package de.augsburg1871.handball.hms.ranking.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class RankingCrawler {

    private static final String TABELLE = "Tabelle";

    public Element readFrom(File file, final Charset charset) throws IOException, RankingNotFoundException {
        return findRanking(Jsoup.parse(file, charset.name()));
    }

    public Element readFrom(InputStream inputStream, Charset charset, String baseUri) throws IOException, RankingNotFoundException {
        return findRanking(Jsoup.parse(inputStream, charset.name(), baseUri));
    }

    public Element readFrom(URL url) throws IOException, RankingNotFoundException {
        //TODO make timeout configurable
        return findRanking(Jsoup.parse(url, 15000));

    }

    private Element findRanking(Document document) throws RankingNotFoundException {
        Elements h2Headlines = document.getElementsByTag("h2");

        for (Element headline : h2Headlines) {
            if (isBlank(headline.text())){
                continue;
            }

            if (equalsIgnoreCase(headline.text(), TABELLE)) {
                return headline.nextElementSibling();
            }
        }

        throw new RankingNotFoundException();
    }

}

package de.augsburg1871.handball.hms.ranking.service;

import de.augsburg1871.handball.hms.common.model.AgeGroup;
import de.augsburg1871.handball.hms.common.model.Season;
import de.augsburg1871.handball.hms.ranking.crawler.RankingCrawler;
import de.augsburg1871.handball.hms.ranking.crawler.RankingNotFoundException;
import de.augsburg1871.handball.hms.ranking.parser.RankingParser;
import de.augsburg1871.handball.hms.ranking.parser.model.RankingTO;
import de.augsburg1871.handball.hms.ranking.persistence.RankingRepository;
import de.augsburg1871.handball.hms.ranking.persistence.mapper.RankingMapper;
import de.augsburg1871.handball.hms.ranking.persistence.model.Ranking;
import de.augsburg1871.handball.hms.ranking.persistence.model.embeddable.RankingId;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DelegatingRankingService implements RankingService {

    private Log log = LogFactory.getLog(this.getClass());

    private RankingRepository rankingRepository;
    private RankingCrawler httpCrawler;

    public DelegatingRankingService(final RankingRepository rankingRepository, RankingCrawler httpCrawler) {
        this.rankingRepository = rankingRepository;
        this.httpCrawler = httpCrawler;
    }

    //TODO configure current season
    @Override
    public Ranking getCurrentRankingFor(AgeGroup ageGroup) {
        return getRankingFor(Season.SEASON_2017_2018, ageGroup);
    }

    @Override
    public Ranking getRankingFor(Season season, AgeGroup ageGroup) {
        Ranking ranking = rankingRepository.getOne(new RankingId(season, ageGroup));
        if (ranking.isUpToDate() || !ranking.isParsingEnabled()) {
            return ranking;
        } else {
            try {
                ranking.setLastCrawl(LocalDateTime.now());
                Element htmlRanking = httpCrawler.readFrom(ranking.getUrl());
                RankingTO rankingTO = RankingParser.parse(htmlRanking);
                RankingMapper.map(ranking, rankingTO);
                ranking = rankingRepository.save(ranking);
            } catch (IOException | RankingNotFoundException e) {
                log.error("Failed to update ranking for " + ageGroup + ".", e);
            }
        }

        return ranking;
    }

    @Override
    public List<Ranking> getRankings(final Season season) {
        return rankingRepository.findAllById_Season(season);
    }
}

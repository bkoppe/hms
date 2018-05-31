package de.augsburg1871.handball.hms.ranking.persistence.mapper;

import de.augsburg1871.handball.hms.ranking.parser.model.RankTO;
import de.augsburg1871.handball.hms.ranking.parser.model.RankingTO;
import de.augsburg1871.handball.hms.ranking.persistence.model.Note;
import de.augsburg1871.handball.hms.ranking.persistence.model.Rank;
import de.augsburg1871.handball.hms.ranking.persistence.model.Ranking;
import de.augsburg1871.handball.hms.ranking.persistence.model.embeddable.RankingId;
import org.junit.Test;

import static de.augsburg1871.handball.hms.common.model.AgeGroup.MAENNER_2;
import static de.augsburg1871.handball.hms.common.model.Season.SEASON_2017_2018;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class RankMapperTest {

    @Test
    public void map(){
        RankTO rankTO = new RankTO();
        rankTO.setRang(9);
        rankTO.setMannschaft("Augsburg 1871 II");
        rankTO.setBegegnungen(18);
        rankTO.setSiege(4);
        rankTO.setUnentschieden(1);
        rankTO.setNiederlagen(13);
        rankTO.setTore("365:581");
        rankTO.setTorDifferenz("-216");
        rankTO.setPunkte("3:33");

        RankingTO rankingTO = new RankingTO();
        rankingTO.addRank(rankTO);

        Ranking ranking = new Ranking(new RankingId(SEASON_2017_2018, MAENNER_2));
        ranking.add(new Note(ranking.getId(), "Augsburg 1871 II", "Test!"));

        RankMapper.map(ranking, rankingTO.getRankTOs());

        // Don't touch existing ranks
        assertThat(ranking.getNotes()).hasSize(1);

        // Verify fileds
        Rank firstRank = ranking.getRanks().first();
        assertThat(firstRank.getPosition()).isEqualTo(9);
        assertThat(firstRank.getTeam()).isEqualTo("Augsburg 1871 II");
        assertThat(firstRank.getGamesPlayed()).isEqualTo(18);
        assertThat(firstRank.getWins()).isEqualTo(4);
        assertThat(firstRank.getDraws()).isEqualTo(1);
        assertThat(firstRank.getLosses()).isEqualTo(13);
        assertThat(firstRank.getGoals()).isEqualTo("365:581");
        assertThat(firstRank.getGoalDifferential()).isEqualTo("-216");
        assertThat(firstRank.getPoints()).isEqualTo("3:33");
    }

}
package de.augsburg1871.handball.hms.ranking.persistence;

import de.augsburg1871.handball.hms.common.model.Season;
import de.augsburg1871.handball.hms.ranking.persistence.model.Ranking;
import de.augsburg1871.handball.hms.ranking.persistence.model.embeddable.RankingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, RankingId> {

//    @Query(value = "select * from ranking_meta_data where season = '2017-18'", nativeQuery = true)
    List<Ranking> findAllById_Season(Season season);

}

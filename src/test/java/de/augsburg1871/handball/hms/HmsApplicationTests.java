package de.augsburg1871.handball.hms;

import de.augsburg1871.handball.hms.common.model.AgeGroup;
import de.augsburg1871.handball.hms.common.model.Season;
import de.augsburg1871.handball.hms.config.HmsProperties;
import de.augsburg1871.handball.hms.ranking.persistence.RankingRepository;
import de.augsburg1871.handball.hms.ranking.persistence.model.Ranking;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HmsApplicationTests {

	@Autowired
	HmsProperties hmsProperties;

	@Autowired
	RankingRepository rankingRepository;

	@Test
	public void contextLoads() {
		Assertions.assertThat(hmsProperties.getCurrentSeason()).isEqualTo(Season.SEASON_2017_2018);

		rankingRepository.save(new Ranking.Builder(Season.SEASON_2017_2018, AgeGroup.MAENNER_1)
				.withUrl("https://www.web.de")
				.build());

		List<Ranking> rankings = rankingRepository.findAll();
		Assertions.assertThat(rankings).hasSize(1);
	}

}

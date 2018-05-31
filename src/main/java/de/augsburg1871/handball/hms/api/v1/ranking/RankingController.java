package de.augsburg1871.handball.hms.api.v1.ranking;

import de.augsburg1871.handball.hms.api.v1.ranking.assembler.RankingResourceAssembler;
import de.augsburg1871.handball.hms.api.v1.ranking.assembler.RankingSummaryResourceAssembler;
import de.augsburg1871.handball.hms.api.v1.ranking.resource.RankingResource;
import de.augsburg1871.handball.hms.api.v1.ranking.resource.RankingSummaryResource;
import de.augsburg1871.handball.hms.common.model.AgeGroup;
import de.augsburg1871.handball.hms.common.model.Season;
import de.augsburg1871.handball.hms.config.HmsProperties;
import de.augsburg1871.handball.hms.ranking.persistence.model.Ranking;
import de.augsburg1871.handball.hms.ranking.service.RankingService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@EnableConfigurationProperties(HmsProperties.class)
@RestController
@RequestMapping(path = "/api/v1/tabellen", produces = APPLICATION_JSON_VALUE)
public class RankingController {

    private final HmsProperties hmsProperties;
    private final RankingService rankingService;

    private final RankingSummaryResourceAssembler rankingSummaryResourceAssembler;
    private final RankingResourceAssembler rankingRresourceAssembler = new RankingResourceAssembler();

    public RankingController(final HmsProperties hmsProperties, final RankingService rankingService, RankingSummaryResourceAssembler rankingSummaryResourceAssembler) {
        this.hmsProperties = hmsProperties;
        this.rankingService = rankingService;

        this.rankingSummaryResourceAssembler = rankingSummaryResourceAssembler;
    }


    @GetMapping(path = "{season}")
    public Resources<RankingSummaryResource> getRankings(@PathVariable String season) {
        Season mappedSeason;
        if ("current".equalsIgnoreCase(season)) {
            mappedSeason = hmsProperties.getCurrentSeason();
        } else {
            mappedSeason = Season.getById(season);
        }

        List<Ranking> rankings = rankingService.getRankings(mappedSeason);
        Resources<RankingSummaryResource> resources = new Resources<>(rankingSummaryResourceAssembler.toResources(rankings));

        if (mappedSeason.getNext().isPresent()) {
            resources.add(linkTo(methodOn(RankingController.class).getRankings(mappedSeason.getNext().get().getId())).withRel("next season"));
        }

        if (mappedSeason.getPrevious().isPresent()) {
            resources.add(linkTo(methodOn(RankingController.class).getRankings(mappedSeason.getPrevious().get().getId())).withRel("previous season"));
        }
        return resources;
    }

    @GetMapping(path = "{season}/{age-group}")
    public RankingResource getRanking(@PathVariable String season, @PathVariable(name = "age-group") String ageGroup) {
        AgeGroup mappedAgeGroup = AgeGroup.getByShortcode(ageGroup);

        Season mappedSeason;
        if ("current".equalsIgnoreCase(season)) {
            mappedSeason = hmsProperties.getCurrentSeason();
        } else {
            mappedSeason = Season.getById(season);
        }

        Ranking ranking = rankingService.getRankingFor(mappedSeason, mappedAgeGroup);
        return rankingRresourceAssembler.toResource(ranking);
    }

}

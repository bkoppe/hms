package de.augsburg1871.handball.hms.api.v1.ranking.assembler;

import de.augsburg1871.handball.hms.api.v1.ranking.RankingController;
import de.augsburg1871.handball.hms.api.v1.ranking.resource.RankingSummaryResource;
import de.augsburg1871.handball.hms.config.HmsProperties;
import de.augsburg1871.handball.hms.ranking.persistence.model.Ranking;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(HmsProperties.class)
public class RankingSummaryResourceAssembler extends ResourceAssemblerSupport<Ranking, RankingSummaryResource> {

    private final HmsProperties properties;

    public RankingSummaryResourceAssembler(HmsProperties properties) {
        super(RankingController.class, RankingSummaryResource.class);
        this.properties = properties;
    }

    @Override
    public RankingSummaryResource toResource(final Ranking ranking) {
        return new RankingSummaryResource(ranking, properties.getTeamNames());
    }

}

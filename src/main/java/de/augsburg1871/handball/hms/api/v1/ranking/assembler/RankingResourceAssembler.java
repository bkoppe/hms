package de.augsburg1871.handball.hms.api.v1.ranking.assembler;

import de.augsburg1871.handball.hms.api.v1.ranking.RankingController;
import de.augsburg1871.handball.hms.api.v1.ranking.resource.RankingResource;
import de.augsburg1871.handball.hms.ranking.persistence.model.Ranking;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RankingResourceAssembler extends ResourceAssemblerSupport<Ranking, RankingResource> {

    public RankingResourceAssembler() {
        super(RankingController.class, RankingResource.class);
    }

    @Override
    public RankingResource toResource(final Ranking entity) {
        return new RankingResource(entity);
    }

}

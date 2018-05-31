package de.augsburg1871.handball.hms.api.v1.ranking.assembler;

import de.augsburg1871.handball.hms.api.v1.ranking.RankingController;
import de.augsburg1871.handball.hms.api.v1.ranking.resource.RankResource;
import de.augsburg1871.handball.hms.ranking.persistence.model.Rank;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RankResourceAssembler extends ResourceAssemblerSupport<Rank, RankResource> {

    public RankResourceAssembler() {
        super(RankingController.class, RankResource.class);
    }

    @Override
    public RankResource toResource(final Rank rank) {
        return new RankResource(rank);
    }
}

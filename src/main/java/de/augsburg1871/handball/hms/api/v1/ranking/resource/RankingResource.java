package de.augsburg1871.handball.hms.api.v1.ranking.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import de.augsburg1871.handball.hms.api.v1.ranking.RankingController;
import de.augsburg1871.handball.hms.ranking.persistence.model.Ranking;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Relation(value = "ranking", collectionRelation = "rankings")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RankingResource extends ResourceSupport {

    private final Ranking ranking;

    public RankingResource(Ranking ranking) {
        this.ranking = ranking;
        this.add(linkTo(methodOn(RankingController.class).getRanking(ranking.getSeason().getId(), ranking.getAgeGroup().getId())).withSelfRel());
    }

    public String getTeam(){
        return ranking.getAgeGroup().getDisplayName();
    }

    public List<RankResource> getRanks() {
        return Lists.newArrayList(ranking.getRanks().stream().map(RankResource::new).collect(Collectors.toList()));
    }

    public List<NoteResource> getNotes() {
        return Lists.newArrayList(ranking.getNotes().stream().map(NoteResource::new).collect(Collectors.toList()));
    }

}

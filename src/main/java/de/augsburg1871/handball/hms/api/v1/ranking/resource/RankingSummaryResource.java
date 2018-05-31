package de.augsburg1871.handball.hms.api.v1.ranking.resource;

import de.augsburg1871.handball.hms.api.v1.ranking.RankingController;
import de.augsburg1871.handball.hms.common.model.AgeGroup;
import de.augsburg1871.handball.hms.ranking.persistence.model.Rank;
import de.augsburg1871.handball.hms.ranking.persistence.model.Ranking;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Relation(value = "rankingSummary", collectionRelation = "rankingSummary")
public class RankingSummaryResource extends ResourceSupport {

    private final Set<String> includedTeams;
    private final AgeGroup team;
    private final int position;
    private final LocalDateTime lastModified;

    public RankingSummaryResource(Ranking ranking, Set<String> includedTeams) {
        this.includedTeams = includedTeams;

        this.team = ranking.getAgeGroup();
        this.position = determinePosition(ranking.getRanks());
        this.lastModified = ranking.getLastCrawl();

        this.add(linkTo(methodOn(RankingController.class).getRanking(ranking.getSeason().getId(), ranking.getAgeGroup().getId())).withSelfRel());
    }

    public String getTeam() {
        return team.getDisplayName();
    }

    public int getPosition() {
        return position;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    private int determinePosition(final SortedSet<Rank> ranks) {
        for (Rank rank : ranks) {
            if (includedTeams.contains(rank.getTeam())) {
                return rank.getPosition();
            }
        }
        throw new IllegalStateException("Couldn't find our team!");
    }

}

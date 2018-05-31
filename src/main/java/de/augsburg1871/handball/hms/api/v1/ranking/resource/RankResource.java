package de.augsburg1871.handball.hms.api.v1.ranking.resource;

import de.augsburg1871.handball.hms.ranking.persistence.model.Rank;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "rank", collectionRelation = "ranks")
public class RankResource extends ResourceSupport {

    private final Rank rank;

    public RankResource(final Rank rank) {
        this.rank = rank;
    }

    public int getPosition(){
        return rank.getPosition();
    }

    public String getTeam(){
        return rank.getTeam();
    }

    public int getGamesPlayed(){
        return rank.getGamesPlayed();
    }

    public int getWins(){
        return rank.getWins();
    }

    public int getDraws(){
        return rank.getDraws();
    }

    public int getLosses(){
        return rank.getLosses();
    }

    public String getGoals(){
        return rank.getGoals();
    }

    public String getGoalDifferential(){
        return rank.getGoalDifferential();
    }

    public String getPoints(){
        return rank.getPoints();
    }

}

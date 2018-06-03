package de.augsburg1871.handball.hms.ranking.persistence.model;

import de.augsburg1871.handball.hms.common.model.AgeGroup;
import de.augsburg1871.handball.hms.common.model.Season;
import de.augsburg1871.handball.hms.ranking.persistence.model.embeddable.RankingDependentId;
import de.augsburg1871.handball.hms.ranking.persistence.model.embeddable.RankingId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Optional;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Table(name = "RANKING_RANKS")
@Entity
public class Rank implements Comparable<Rank> {

    @EmbeddedId
    private RankingDependentId id;

    @MapsId("id")
    @JoinColumns({
            @JoinColumn(name = "season", referencedColumnName = "season"),
            @JoinColumn(name = "ageGroup", referencedColumnName = "ageGroup")
    })
    @ManyToOne
    public Ranking ranking;

    private int position;
    private int gamesPlayed;
    private int wins;
    private int draws;
    private int losses;

    @Column(length = 8)
    private String goals;

    @Column(length = 5)
    private String goalDifferential;

    @Column(length = 5)
    private String points;

    public RankingDependentId getId() {
        return id;
    }

    public void setId(final RankingDependentId id) {
        this.id = id;
    }

    @Transient
    public Season getSeason(){
        return this.id.getRankingId().getSeason();
    }

    @Transient
    public AgeGroup getAgeGroup(){
        return this.id.getRankingId().getAgeGroup();
    }

    @Transient
    public String getTeam(){
        return id.getTeamName();
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(final Ranking ranking) {
        this.ranking = ranking;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(final int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(final int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(final int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(final int losses) {
        this.losses = losses;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(final String goals) {
        this.goals = goals;
    }

    public String getGoalDifferential() {
        return goalDifferential;
    }

    public void setGoalDifferential(final String goalDifferential) {
        this.goalDifferential = goalDifferential;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(final String points) {
        this.points = points;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Rank rank = (Rank) o;

        return new EqualsBuilder()
                .append(id, rank.id)
                .append(position, rank.position)
                .append(gamesPlayed, rank.gamesPlayed)
                .append(points, rank.points)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(position)
                .append(gamesPlayed)
                .append(points)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("ranking", ranking)
                .append("team", this.getId().getTeamName())
                .toString();
    }

    @Override
    public int compareTo(final Rank o) {
        return Comparator.comparing(Rank::getPosition).compare(this, o);
    }

    public static class Builder {

        private Optional<Ranking> ranking = Optional.empty();

        private final RankingDependentId id;
        private final String team;
        private int position;
        private int gamesPlayed;
        private int wins;
        private int draws;
        private int losses;
        private String goalDifferential;
        private String goals;
        private String points;

        public Builder(final Ranking ranking, final String team) {
            this(ranking.getId(), team);
            this.ranking = Optional.of(ranking);
        }

        public Builder(final Season season, final AgeGroup ageGroup, final String team) {
            this(new RankingId(season, ageGroup), team);
        }

        public Builder(final RankingId rankingId, final String team) {
            this.id = new RankingDependentId(rankingId, team);
            this.team = team;
        }

        public Builder position(final int position){
            this.position = position;
            return this;
        }

        public Builder gamesPlayed(final int gamesPlayed) {
            this.gamesPlayed = gamesPlayed;
            return this;
        }

        public Builder wins(final int wins){
            this.wins = wins;
            return this;
        }

        public Builder draws(final int draws){
            this.draws = draws;
            return this;
        }

        public Builder losses(final int losses){
            this.losses = losses;
            return this;
        }

        public Builder goalDifferential(final String goalDifferential){
            this.goalDifferential = goalDifferential;
            return this;
        }

        public Builder goals(final String goals){
            this.goals = goals;
            return this;
        }

        public Builder points(final String points){
            this.points = points;
            return this;
        }

        public Rank build() {
            final Rank rank = new Rank();
            rank.setId(this.id);
            rank.setPosition(this.position);
            rank.setGamesPlayed(this.gamesPlayed);
            rank.setWins(this.wins);
            rank.setDraws(this.draws);
            rank.setLosses(this.losses);
            rank.setGoalDifferential(this.goalDifferential);
            rank.setGoals(this.goals);
            rank.setPoints(this.points);

            if (ranking.isPresent()){
                ranking.get().add(rank);
            }

            return rank;
        }
    }

}

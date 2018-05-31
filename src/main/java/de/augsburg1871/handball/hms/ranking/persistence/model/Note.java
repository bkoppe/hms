package de.augsburg1871.handball.hms.ranking.persistence.model;

import de.augsburg1871.handball.hms.common.model.AgeGroup;
import de.augsburg1871.handball.hms.common.model.Season;
import de.augsburg1871.handball.hms.ranking.persistence.model.embeddable.RankingDependentId;
import de.augsburg1871.handball.hms.ranking.persistence.model.embeddable.RankingId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Table(name = "NOTES")
@Entity
public class Note {

    @EmbeddedId
    private RankingDependentId id;

    @MapsId("id")
    @JoinColumns({
            @JoinColumn(name = "season", referencedColumnName = "season"),
            @JoinColumn(name = "ageGroup", referencedColumnName = "ageGroup")
    })
    @ManyToOne
    public Ranking ranking;

    @Column(length = 50)
    private String note;

    private Note() {
    }

    public Note(final RankingId rankingId, final String team, final String note){
        this.id = new RankingDependentId(rankingId, team);
        this.note = note;
    }

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
    public String getTeam() {
        return id.getTeamName();
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Note note = (Note) o;

        return new EqualsBuilder()
                .append(this.getId().getTeamName(), note.getId().getTeamName())
                .append(this.note, note.getNote())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id.getTeamName())
                .append(note)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("team", id.getTeamName())
                .append("note", note)
                .toString();
    }

    public static final class Builder {

        private Ranking ranking;

        private final String team;
        private final String note;

        public Builder(final Ranking ranking, final String team, final String note){
            checkNotNull(ranking, "Ranking must note be null!");
            checkArgument(isNotBlank(team), "Team must not be blank!");
            checkArgument(isNotBlank(note), "Note must not be blank!");

            this.ranking = ranking;
            this.team = team;
            this.note = note;
        }

        public Note build() {
            Note note = new Note(ranking.getId(), this.team, this.note);
            ranking.add(note);

            return note;
        }

    }

}

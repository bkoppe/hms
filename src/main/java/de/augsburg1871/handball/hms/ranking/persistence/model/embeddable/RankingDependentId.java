package de.augsburg1871.handball.hms.ranking.persistence.model.embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Embeddable
public class RankingDependentId implements Serializable {

    private static final long serialVersionUID = -2803058074898380439L;

    @Column(nullable = false)
    private RankingId rankingId;

    @Column(nullable = false, length = 50)
    private String teamName;

    private RankingDependentId() {
    }

    public RankingDependentId(final RankingId rankingId, final String teamName) {
        this.rankingId = rankingId;
        this.teamName = teamName;
    }

    public RankingId getRankingId() {
        return rankingId;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final RankingDependentId id = (RankingDependentId) o;

        return new EqualsBuilder()
                .append(rankingId, id.rankingId)
                .append(teamName, id.teamName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(rankingId)
                .append(teamName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("rankingId", rankingId)
                .append("teamName", teamName)
                .toString();
    }

}

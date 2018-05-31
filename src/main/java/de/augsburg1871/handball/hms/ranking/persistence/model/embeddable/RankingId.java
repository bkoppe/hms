package de.augsburg1871.handball.hms.ranking.persistence.model.embeddable;

import de.augsburg1871.handball.hms.common.model.AgeGroup;
import de.augsburg1871.handball.hms.common.model.Season;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Embeddable
public class RankingId implements Serializable {

    private static final long serialVersionUID = -5830397090504801029L;

    @Column(nullable = false, length = 9, updatable = false)
    private Season season;

    @Column(nullable = false, length=12, updatable = false)
    private AgeGroup ageGroup;

    private RankingId(){}

    public RankingId(final Season season, final AgeGroup ageGroup) {
        this.season = season;
        this.ageGroup = ageGroup;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(final Season season) {
        this.season = season;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(final AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final RankingId rankingId = (RankingId) o;

        return new EqualsBuilder()
                .append(season, rankingId.season)
                .append(ageGroup, rankingId.ageGroup)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(season)
                .append(ageGroup)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("season", season)
                .append("ageGroup", ageGroup)
                .toString();
    }

}

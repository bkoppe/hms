package de.augsburg1871.handball.hms.ranking.persistence.model;

import com.google.common.collect.Sets;
import de.augsburg1871.handball.hms.common.model.AgeGroup;
import de.augsburg1871.handball.hms.common.model.Season;
import de.augsburg1871.handball.hms.ranking.persistence.model.embeddable.RankingId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.persistence.FetchType.EAGER;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.hibernate.annotations.CascadeType.ALL;
import static org.springframework.util.CollectionUtils.isEmpty;

@Table(name = "RANKING_META_DATA")
@Entity
public class Ranking {

    @EmbeddedId
    private RankingId id;

    @Column(nullable = false)
    private URL url;

    @Column(nullable = false)
    private boolean isParsingEnabled;

    @Cascade(ALL)
    @OneToMany(mappedBy = "ranking", fetch = EAGER)
    @OrderBy("position")
    private SortedSet<Rank> ranks = new TreeSet<>(Comparator.comparing(Rank::getPosition));

    @Cascade(ALL)
    @OneToMany(mappedBy = "ranking", fetch = EAGER)
    private Set<Note> notes = Sets.newHashSet();

    @Column
    private LocalDateTime lastCrawl;

    protected Ranking() {
    }

    public Ranking(final RankingId id) {
        this.id = id;
        this.isParsingEnabled = true;
    }

    public Ranking(final Season season, final AgeGroup ageGroup) {
        this(new RankingId(season, ageGroup));
    }

    public RankingId getId() {
        return id;
    }

    public void setId(final RankingId id) {
        this.id = id;
    }

    @Transient
    public Season getSeason(){
        return this.id.getSeason();
    }

    @Transient
    public AgeGroup getAgeGroup(){
        return this.id.getAgeGroup();
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(final URL url) {
        this.url = url;
    }

    public boolean isParsingEnabled() {
        return isParsingEnabled;
    }

    public void setParsingEnabled(final boolean parsingEnabled) {
        isParsingEnabled = parsingEnabled;
    }

    public SortedSet<Rank> getRanks() {
        return ranks;
    }

    public void setRanks(final SortedSet<Rank> ranks) {
        this.ranks = ranks;
    }

    public void add(Rank rank) {
        ranks.add(rank);
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(final Set<Note> notes) {
        this.notes = notes;
    }

    public void add(final Note note) {
        this.notes.add(note);
    }

    public LocalDateTime getLastCrawl() {
        return lastCrawl;
    }

    public void setLastCrawl(final LocalDateTime lastCrawl) {
        this.lastCrawl = lastCrawl;
    }

    //TODO make me configurable
    @Transient
    public boolean isUpToDate() {
        if (getLastCrawl() == null) {
            return false;
        }

        return LocalDateTime.now().minusMinutes(15).isBefore(getLastCrawl()) && !isEmpty(getRanks());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Ranking ranking = (Ranking) o;

        return new EqualsBuilder()
                .append(getId(), ranking.getId())
                .append(getLastCrawl(), ranking.getLastCrawl())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getLastCrawl())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("season", getSeason())
                .append("ageGroup", getAgeGroup())
                .toString();
    }

    public static final class Builder {

        private final RankingId rankingId;
        private URL url;
        private boolean parsingEnabled;
        private SortedSet<Rank> ranks = new TreeSet<>(Comparator.comparing(Rank::getPosition));

        public Builder(final Season season, final AgeGroup ageGroup) {
            checkNotNull(season, "Season must not be null!");
            checkNotNull(ageGroup, "AgeGroup must not be null!");

            this.rankingId = new RankingId(season, ageGroup);
        }

        public Builder withUrl(String url) {
            try {
                return withUrl(new URL(url));
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Couldn't create URL from " + url + ".", e);
            }
        }

        public Builder withUrl(URL url) {
            checkNotNull(url);

            this.url = url;
            return this;
        }

        public Builder withParsingEnabled(final boolean parsingEnabled) {
            this.parsingEnabled = parsingEnabled;
            return this;
        }

        public Builder withRanks(Rank... ranks) {
            this.ranks.addAll(Arrays.asList(ranks));
            return this;
        }

        public Ranking build() {
            final Ranking ranking = new Ranking(rankingId);
            ranking.setUrl(this.url);
            ranking.setParsingEnabled(this.parsingEnabled);
            ranking.setLastCrawl(LocalDateTime.now().minusWeeks(2));
            ranking.setRanks(ranks);
            return ranking;
        }

    }

}

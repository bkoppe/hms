package de.augsburg1871.handball.hms.config;

import com.google.common.collect.Sets;
import de.augsburg1871.handball.hms.common.model.Season;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties("hms")
public class HmsProperties {

    /**
     * <p>Die aktuelle Saison. Wichtig für den Context des Systems.</p>
     */
    private Season currentSeason;

    /**
     * <p>Mannschaftsbezeichnungen unter denen unser Verein beim BHV geführt wird.</p>
     */
    private Set<String> teamNames = Sets.newHashSet("Augsburg 1871", "Augsburg 1871 II", "SG Augsburg-Gersthofen");


    public Season getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(final Season currentSeason) {
        this.currentSeason = currentSeason;
    }

    public Set<String> getTeamNames() {
        return teamNames;
    }

    public void setTeamNames(final Set<String> teamNames) {
        this.teamNames = teamNames;
    }

}

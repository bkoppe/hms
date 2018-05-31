package de.augsburg1871.handball.hms.ranking.crawler;


import de.augsburg1871.handball.hms.common.model.AgeGroup;

import java.net.URL;

public class RankingNotFoundException extends Throwable {

    public RankingNotFoundException() {
        super();
    }

    public RankingNotFoundException(final URL url) {
        super("Couldn't read ranking from " + url + ".");
    }

    public RankingNotFoundException(final AgeGroup ageGroup, final URL url) {
        super("Could not read ranking for " + ageGroup + " from " + url + ".");
    }

}

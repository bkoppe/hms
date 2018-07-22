package de.augsburg1871.handball.hms.common.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public enum Season {

    SEASON_2016_2017("2016-17", 2016),
    SEASON_2017_2018("2017-18", 2017),
    SEASON_2018_2019("2018-19", 2018),
    SEASON_2019_2020("2019-20", 2019);

    private static final Map<String, Season> LOOKUP = Maps.newHashMap();
    private static final List<Season> VALUES = Lists.newArrayList(values());

    static {
        for (Season season : Season.values()) {
            LOOKUP.put(season.getId(), season);
        }
    }

    private final String id;
    private final int startsInYear;

    Season(final String id, final int startsInYear) {
        this.id = id;
        this.startsInYear = startsInYear;
    }

    public static final Season getById(String id) {
        checkArgument(isNotBlank(id), "ID must not be blank!");
        return LOOKUP.get(id);
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return "Saison " + startsInYear + "/" + startsInYear + 1;
    }

    public Optional<Season> getPrevious() {
        int index = this.ordinal();
        if (index < 1) {
            return Optional.empty();
        }

        return Optional.of(VALUES.get(index - 1));
    }

    public Optional<Season> getNext() {
        int index = this.ordinal();
        if (index >= VALUES.size() - 1) {
            return Optional.empty();
        }

        return Optional.of(VALUES.get(index + 1));
    }

    @Converter(autoApply = true)
    public static final class SeasonConverter implements AttributeConverter<Season, String> {

        @Override
        public String convertToDatabaseColumn(final Season attribute) {
            return attribute.getId();
        }

        @Override
        public Season convertToEntityAttribute(final String dbData) {
            if (isBlank(dbData)) {
                return null;
            }

            return Season.getById(dbData);
        }

    }

}

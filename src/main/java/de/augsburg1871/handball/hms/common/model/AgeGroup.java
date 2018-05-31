package de.augsburg1871.handball.hms.common.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static de.augsburg1871.handball.hms.common.model.Sex.FEMALE;
import static de.augsburg1871.handball.hms.common.model.Sex.MALE;
import static de.augsburg1871.handball.hms.common.model.Sex.UNISEX;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public enum AgeGroup {

    MINIS("minis", UNISEX, "Minis"),
    GEMISCHTE_E_JUGEND("gem-e", UNISEX, "gemischte E-Jugend"),
    MAENNLICHE_D_JUGEND("m-d", MALE, "männliche D-Jugend"),
    MAENNLICHE_C_JUGEND("m-c", MALE, "männliche C-Jugend"),
    MAENNLICHE_B_JUGEND("m-b", MALE, "männliche B-Jugend"),
    MAENNLICHE_A_JUGEND("m-a", MALE, "männliche A-Jugend"),
    FRAUEN("f-1", FEMALE, "Frauen"),
    MAENNER_1("m-1", MALE, "Männer I"),
    MAENNER_2("m-2", MALE, "Männer II");

    private static final Map<String, AgeGroup> LOOKUP = new HashMap<String, AgeGroup>();

    static {
        for (AgeGroup ageGroup : AgeGroup.values()) {
            LOOKUP.put(ageGroup.getId(), ageGroup);
        }
    }

    private final String id;
    private final Sex sex;
    private final String displayName;

    AgeGroup(final String id, final Sex sex, String displayName) {
        this.id = id;
        this.sex = sex;
        this.displayName = displayName;
    }

    public static AgeGroup getByShortcode(String shortcode) {
        checkArgument(isNotBlank(shortcode), "Shortcode must not be blank!");
        return LOOKUP.get(shortcode);
    }

    public String getId() {
        return id;
    }

    public Sex getSex() {
        return sex;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Converter(autoApply = true)
    public static final class AgeGroupConverter implements AttributeConverter<AgeGroup, String> {

        @Override
        public String convertToDatabaseColumn(final AgeGroup attribute) {
            return attribute.getId();
        }

        @Override
        public AgeGroup convertToEntityAttribute(final String dbData) {
            if (isBlank(dbData)) {
                return null;
            }

            return AgeGroup.getByShortcode(dbData);
        }
    }

}

package de.augsburg1871.handball.hms.ranking.parser;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ValueExtractor {

    private static Log LOG = LogFactory.getLog(ValueExtractor.class);

    public static String getAsString(final String value)
    {
        if(isBlank(value)){
            return null;
        }

        return cleanString(value);
    }

    public static int getAsInt(final String value){
        String cleanValue = cleanString(value);
        return Integer.parseInt(cleanValue);
    }

    private static final List<String> STRINGS_TO_REMOVE = Lists.newArrayList("\u00a0");

    private static String cleanString(final String value)
    {
        String cleanValue = value;
        for (String toRemove : STRINGS_TO_REMOVE) {
            cleanValue = cleanValue.replace(toRemove, "");
        }

        return cleanValue.trim();
    }
}

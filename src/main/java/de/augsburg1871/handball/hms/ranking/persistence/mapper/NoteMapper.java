package de.augsburg1871.handball.hms.ranking.persistence.mapper;


import de.augsburg1871.handball.hms.ranking.parser.model.NoteTO;
import de.augsburg1871.handball.hms.ranking.persistence.model.Note;
import de.augsburg1871.handball.hms.ranking.persistence.model.Ranking;

import java.util.Set;

public final class NoteMapper {

    public static final Ranking map(Ranking ranking, Set<NoteTO> noteTOs) {
        for (NoteTO note : noteTOs) {
            new Note.Builder(ranking, note.getMannschaft(), note.getHinweis())
                    .build();
        }

        return ranking;
    }

}

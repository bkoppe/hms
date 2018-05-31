package de.augsburg1871.handball.hms.api.v1.ranking.resource;

import de.augsburg1871.handball.hms.ranking.persistence.model.Note;
import org.springframework.hateoas.ResourceSupport;

public class NoteResource extends ResourceSupport {

    private final Note note;

    public NoteResource(final Note note) {
        this.note = note;
    }

    public String getTeam(){
        return note.getTeam();
    }

    public String getNote(){
        return note.getNote();
    }

}

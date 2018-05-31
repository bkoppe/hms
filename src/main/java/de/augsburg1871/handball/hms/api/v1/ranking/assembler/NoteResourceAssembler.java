package de.augsburg1871.handball.hms.api.v1.ranking.assembler;

import de.augsburg1871.handball.hms.api.v1.ranking.RankingController;
import de.augsburg1871.handball.hms.api.v1.ranking.resource.NoteResource;
import de.augsburg1871.handball.hms.ranking.persistence.model.Note;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class NoteResourceAssembler extends ResourceAssemblerSupport<Note, NoteResource> {

    public NoteResourceAssembler() {
        super(RankingController.class, NoteResource.class);
    }

    @Override
    public NoteResource toResource(final Note entity) {
        return new NoteResource(entity);
    }

}

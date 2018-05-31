package de.augsburg1871.handball.hms.ranking.parser.model;

import com.google.common.collect.Sets;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class RankingTO {

    private SortedSet<RankTO> rankTOs = new TreeSet<>(Comparator.comparing(RankTO::getRang));
    private Set<NoteTO> noteTOs = Sets.newHashSet();

    public SortedSet<RankTO> getRankTOs() {
        return rankTOs;
    }

    public void setRankTOs(final SortedSet<RankTO> rankTOs) {
        this.rankTOs = rankTOs;
    }

    public void addRank(RankTO rank) {
        this.rankTOs.add(rank);
    }

    public Set<NoteTO> getNoteTOs() {
        return noteTOs;
    }

    public void setNoteTOs(final Set<NoteTO> noteTOs) {
        this.noteTOs = noteTOs;
    }

    public void addNote(NoteTO noteTO) {
        this.noteTOs.add(noteTO);
    }

}

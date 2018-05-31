package de.augsburg1871.handball.hms.ranking.parser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class NoteTO {

    private int rang;
    private String mannschaft;
    private String hinweis;

    public NoteTO() {
    }

    public NoteTO(final int rang, final String mannschaft, final String hinweis) {
        this.rang = rang;
        this.mannschaft = mannschaft;
        this.hinweis = hinweis;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(final int rang) {
        this.rang = rang;
    }

    public String getMannschaft() {
        return mannschaft;
    }

    public void setMannschaft(final String mannschaft) {
        this.mannschaft = mannschaft;
    }

    public String getHinweis() {
        return hinweis;
    }

    public void setHinweis(final String hinweis) {
        this.hinweis = hinweis;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final NoteTO noteTO = (NoteTO) o;

        return new EqualsBuilder()
                .append(mannschaft, noteTO.mannschaft)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(mannschaft)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("mannschaft", mannschaft)
                .append("hinweis", hinweis)
                .toString();
    }

}

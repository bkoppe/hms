package de.augsburg1871.handball.hms.ranking.parser.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RankTO {

    private int rang;
    private String mannschaft;
    private int begegnungen;
    private int siege;
    private int unentschieden;
    private int niederlagen;
    private String tore;
    private String torDifferenz;
    private String punkte;

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

    public int getBegegnungen() {
        return begegnungen;
    }

    public void setBegegnungen(final int begegnungen) {
        this.begegnungen = begegnungen;
    }

    public int getSiege() {
        return siege;
    }

    public void setSiege(final int siege) {
        this.siege = siege;
    }

    public int getUnentschieden() {
        return unentschieden;
    }

    public void setUnentschieden(final int unentschieden) {
        this.unentschieden = unentschieden;
    }

    public int getNiederlagen() {
        return niederlagen;
    }

    public void setNiederlagen(final int niederlagen) {
        this.niederlagen = niederlagen;
    }

    public String getTore() {
        return tore;
    }

    public void setTore(final String tore) {
        this.tore = tore;
    }

    public String getTorDifferenz() {
        return torDifferenz;
    }

    public void setTorDifferenz(final String torDifferenz) {
        this.torDifferenz = torDifferenz;
    }

    public String getPunkte() {
        return punkte;
    }

    public void setPunkte(final String punkte) {
        this.punkte = punkte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RankTO rank = (RankTO) o;

        return new EqualsBuilder()
                .append(rang, rank.getRang())
                .append(mannschaft, rank.getMannschaft())
                .append(punkte, rank.getPunkte())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(rang)
                .append(mannschaft)
                .append(punkte)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Rang", rang)
                .append("Mannschaft", mannschaft)
                .toString();
    }

}

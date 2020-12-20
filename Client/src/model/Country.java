package model;

import java.io.Serializable;
import java.util.Objects;

public class Country implements Serializable {
    private int idCountry;
    private String nameCountry;
    public Country(){
        this.idCountry = 0;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCountry, this.nameCountry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country that = (Country) o;
        return Objects.equals(this.idCountry, that.idCountry) &&
                Objects.equals(this.nameCountry, that.nameCountry);
    }

    @Override
    public String toString() {
        return "Countries{" +
                " idCountry='" + idCountry + '\'' +
                ", nameCountry='" + nameCountry + '\'' +
                '}';
    }
}

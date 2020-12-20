package model;

import java.io.Serializable;
import java.util.Objects;

public class Multiplier implements Serializable {
    private int idMult;
    private float value;
    private String nameMult;
    private int idCompany;
    public Multiplier(){
        this.idMult = 0;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public float getValue() {
        return value;
    }

    public String getNameMult() {
        return nameMult;
    }

    public int getIdMult() {
        return idMult;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public void setIdMult(int idMult) {
        this.idMult = idMult;
    }

    public void setNameMult(String incomeMult) {
        this.nameMult = incomeMult;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idMult, this.value,this.nameMult,this.idCompany);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Multiplier that = (Multiplier) o;
        return Objects.equals(this.idMult, that.idMult) &&
                Objects.equals(this.nameMult, that.nameMult)&&
                Objects.equals(this.value, that.value)&&
                Objects.equals(this.idCompany, that.idCompany) ;
    }

    @Override
    public String toString() {
        return "Multiplier{" +
                " idMult='" + idMult +  '\'' +
                ", value='" + value + '\'' +
                ", nameMult='" + nameMult + '\'' +
                ", idCompany='" + idCompany + '\'' +
                '}';
    }
}

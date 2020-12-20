package model;

import java.io.Serializable;
import java.util.Objects;

public class FinParameter implements Serializable {
    private int idParameter;
    private String nameParameter;
    private float value;
    private int idCompany;
    public FinParameter(){ this.idParameter=0;}

    public int getIdCompany() {
        return idCompany;
    }

    public float getValue() {
        return value;
    }

    public int getIdParameter() {
        return idParameter;
    }

    public String getNameParameter() {
        return nameParameter;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public void setIdParameter(int idParameter) {
        this.idParameter = idParameter;
    }

    public void setNameParameter(String nameParameter) {
        this.nameParameter = nameParameter;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCompany, this.idParameter, this.nameParameter, this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinParameter that = (FinParameter) o;

        return Objects.equals(this.idCompany, that.idCompany) &&
                Objects.equals(this.idParameter, that.idParameter) &&
                Objects.equals(this.value, that.value) &&
                Objects.equals(this.nameParameter, that.nameParameter);
    }

    @Override
    public String toString() {
        return "FinParameter {" +
                " idCompany='" + idCompany + '\'' +
                ", nameParameter='" +nameParameter + '\'' +
                ", value='" + value + '\'' +
                ", idParameter='" + idParameter + '\'' +
                '}';
    }
}

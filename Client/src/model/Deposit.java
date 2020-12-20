package model;

import java.io.Serializable;
import java.util.Objects;

public class Deposit implements Serializable {
    private int idDeposit;
    private float value;
    private String nameDeposit;
    private int idCompany;
    public Deposit(){
        this.idDeposit = 0;
    }
    public int getIdCompany() {
        return idCompany;
    }

    public String getNameDeposit() {
        return nameDeposit;
    }

    public float getValue() {
        return value;
    }

    public int getIdDeposit() {
        return idDeposit;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public void setIdDeposit(int idMult) {
        this.idDeposit = idMult;
    }

    public void setNameDeposit(String nameDeposit) {
        this.nameDeposit = nameDeposit;
    }

    public void setValue(float proceedsDeposit) {
        this.value = proceedsDeposit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idDeposit, this.value,this.nameDeposit,this.idCompany);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deposit that = (Deposit) o;
        return Objects.equals(this.idDeposit, that.idDeposit) &&
                Objects.equals(this.value, that.value)&&
                Objects.equals(this.nameDeposit, that.nameDeposit)&&
                Objects.equals(this.idCompany, that.idCompany) ;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                " idDeposit='" + idDeposit + '\'' +
                ", value='" + value + '\'' +
                ", nameDeposit='" + nameDeposit + '\'' +
                ", idCompany='" + idCompany + '\'' +
                '}';
    }
}
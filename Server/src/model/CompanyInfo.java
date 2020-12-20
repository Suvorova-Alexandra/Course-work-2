package model;

import java.io.Serializable;
import java.util.Objects;

public class CompanyInfo implements Serializable {
    private int idUser;
    private int idCompany;
    public CompanyInfo(){ this.idCompany=0;
    this.idUser=0;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCompany, this.idUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyInfo that = (CompanyInfo) o;
        return Objects.equals(this.idCompany, that.idCompany) &&
                Objects.equals(this.idUser, that.idUser);
    }

    @Override
    public String toString() {
        return "CompanyInfo {" +
                " idCompany='" + idCompany + '\'' +
                ", idUser='" +idUser + '\'' +
                '}';
    }
}

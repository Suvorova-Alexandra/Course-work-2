package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Company implements Serializable  {
    protected int idCompany;
    protected String nameCompany;
    protected int sizeCompany;
    protected String industryCompany;
    protected int idCountry;
    protected String typeCompany;
    private ArrayList<FinParameter> parameters;

    public Company(){ this.idCompany=0;}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company that = (Company) o;

        return Objects.equals(this.idCompany, that.idCompany) &&
                Objects.equals(this.nameCompany, that.nameCompany) &&
                Objects.equals(this.sizeCompany, that.sizeCompany) &&
                Objects.equals(this.industryCompany, that.industryCompany) &&
                Objects.equals(this.idCountry, that.idCountry) &&
                Objects.equals(this.typeCompany, that.typeCompany) &&
                Objects.equals(this.parameters, that.parameters) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCompany, this.nameCompany, this.sizeCompany, this.industryCompany, this.idCountry,this.typeCompany,this.parameters);
    }

    @Override
    public String toString() {
        return "Company {" +
                " idCompany='" + idCompany + '\'' +
                ", nameCompany='" +nameCompany + '\'' +
                ", sizeCompany='" + sizeCompany + '\'' +
                ", industryCompany='" + industryCompany + '\'' +
                ", idCountry='" +idCountry + '\'' +
                ", typeCompany='" + typeCompany + '\'' +
                '}';
    }


    public void setParameters(ArrayList<FinParameter> parameters) {
        this.parameters = parameters;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public void setIndustryCompany(String industryCompany) {
        this.industryCompany = industryCompany;
    }

    public void setSizeCompany(int sizeCompany) {
        this.sizeCompany = sizeCompany;
    }

    public void setTypeCompany(String typeCompany) {
        this.typeCompany = typeCompany;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }
    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }
    public String getNameCompany() {
        return nameCompany;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public String getTypeCompany() {
        return typeCompany;
    }

    public String getIndustryCompany() {
        return industryCompany;
    }

    public int getSizeCompany() {
        return sizeCompany;
    }



    public int getIdCompany() {
        return idCompany;
    }
    public ArrayList<FinParameter> getParameters() {
        return parameters;
    }


}

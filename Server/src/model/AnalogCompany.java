package model;

import java.io.Serializable;
import java.util.Objects;

public class AnalogCompany extends Company implements Serializable {
    private float costCompany;
    public AnalogCompany(){
        costCompany=0;
    }

    public void setCostCompany(float costCompany) {
        this.costCompany = costCompany;
    }

    public float getCostCompany() {
        return costCompany;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnalogCompany that = (AnalogCompany) o;

        return Objects.equals(this.idCompany, that.idCompany) &&
                Objects.equals(this.nameCompany, that.nameCompany) &&
                Objects.equals(this.sizeCompany, that.sizeCompany) &&
                Objects.equals(this.industryCompany, that.industryCompany) &&
                Objects.equals(this.idCountry, that.idCountry) &&
                Objects.equals(this.typeCompany, that.typeCompany) &&
                Objects.equals(this.costCompany, that.costCompany) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCompany, this.nameCompany, this.sizeCompany, this.industryCompany, this.idCountry,this.typeCompany, this.costCompany);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", costCompany='" + costCompany + '\'' +
                '}';
    }
}

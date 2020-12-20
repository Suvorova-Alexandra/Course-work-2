package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class EvaluatedCompany extends Company implements Serializable {
    private ArrayList<Multiplier> multipliers;
    private  ArrayList<Deposit> deposits;

    public EvaluatedCompany(){
        multipliers=new ArrayList<>();
        deposits=new ArrayList<>();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluatedCompany that = (EvaluatedCompany) o;

        return Objects.equals(this.idCompany, that.idCompany) &&
                Objects.equals(this.nameCompany, that.nameCompany) &&
                Objects.equals(this.sizeCompany, that.sizeCompany) &&
                Objects.equals(this.industryCompany, that.industryCompany) &&
                Objects.equals(this.idCountry, that.idCountry) &&
                Objects.equals(this.typeCompany, that.typeCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCompany, this.nameCompany, this.sizeCompany, this.industryCompany, this.idCountry, this.typeCompany);
    }

    @Override
    public String toString() {
        return super.toString() +

                '}';
    }

    public ArrayList<Deposit> getDeposits() {
        return deposits;
    }

    public ArrayList<Multiplier> getMultipliers() {
        return multipliers;
    }

    public void setMultipliers(ArrayList<Multiplier> multipliers) {
        this.multipliers = multipliers;
    }

    public void setDeposits(ArrayList<Deposit> deposits) {
        this.deposits = deposits;
    }
}

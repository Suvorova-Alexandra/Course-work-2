package database;

public abstract class AbstractFactory {
    public abstract TableUsers getUsers();
    public abstract TableEvaluatedCompany getCompanies();
    public abstract TableAnalogCompany getAnalogCompanies();
    public abstract TableCompanyInfo getInfo();
    public abstract TableParameter getParameters();
    public abstract TableMultiplier getMultipliers();
    public abstract TableDeposit getDeposits();
    public abstract TableCountry getCountries();
    public abstract TableReport getReports();
}

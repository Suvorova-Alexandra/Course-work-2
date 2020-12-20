package database;

public class TableFactory extends AbstractFactory{
    @Override
    public TableUsers getUsers() {
        return TableUsers.getInstance();
    }
    @Override
    public TableEvaluatedCompany getCompanies() {
        return TableEvaluatedCompany.getInstance();
    }

    @Override
    public TableAnalogCompany getAnalogCompanies() {
        return TableAnalogCompany.getInstance();
    }

    @Override
    public TableCompanyInfo getInfo() {
        return TableCompanyInfo.getInstance();
    }

    @Override
    public TableParameter getParameters() {
        return TableParameter.getInstance();
    }

    @Override
    public TableMultiplier getMultipliers() {
        return TableMultiplier.getInstance();
    }
    @Override
    public TableDeposit getDeposits() {
        return TableDeposit.getInstance();
    }
    @Override
    public TableCountry getCountries() {
        return TableCountry.getInstance();
    }

    @Override
    public TableReport getReports() {
        return TableReport.getInstance();
    }
}

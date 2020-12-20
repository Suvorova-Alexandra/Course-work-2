package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Report  implements Serializable {
    private int idReport;
    private float costReport;
    private Date dateReport;
    private int idCompany;
    private SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
    public Report(){
        idReport=0;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public String getDateReport() {

        return formatter.format(dateReport);
    }
    public float getCostReport() {
        return costReport;
    }

    public int getIdReport() {
        return idReport;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public void setCostReport(float costReport) {
        this.costReport = costReport;
    }

    public void setDateReport(String dateReport) {
        try {
            this.dateReport = formatter.parse(dateReport);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idReport, this.costReport,this.dateReport,this.idCompany);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report that = (Report) o;
        return Objects.equals(this.idReport, that.idReport) &&
                Objects.equals(this.costReport, that.costReport)&&
                Objects.equals(this.dateReport, that.dateReport)&&
                Objects.equals(this.idCompany, that.idCompany) ;
    }

    @Override
    public String toString() {
        return "Report{" +
                " idReport='" + idReport +  '\'' +
                ", costCompany='" + costReport + '\'' +
                ", dateReport='" + dateReport + '\'' +
                ", idCompany='" + idCompany + '\'' +
                '}';
    }
}

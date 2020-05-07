package orm;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invoice", schema = "crdb")
public class InvoiceEntity {
    private int id;
    private String companyname;
    private String billto;
    private double amount;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "companyname")
    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    @Basic
    @Column(name = "billto")
    public String getBillto() {
        return billto;
    }

    public void setBillto(String billto) {
        this.billto = billto;
    }

    @Basic
    @Column(name = "amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceEntity that = (InvoiceEntity) o;
        return id == that.id &&
                Double.compare(that.amount, amount) == 0 &&
                Objects.equals(companyname, that.companyname) &&
                Objects.equals(billto, that.billto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyname, billto, amount);
    }
}

package com.syntech.sbs.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "client_layout_preferences")
public class ClientLayoutPreferences extends BaseIdEntity{

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "company_name_align")
    private String companyNameAlign;

    @Column(name = "address_align")
    private String addressAlign;

    @Column(name = "date_align")
    private String dateAlign;

    @Column(name = "customer_name_align")
    private String customerNameAlign;

    @Column(name = "sales_invoice_align")
    private String salesInvoiceAlign;

    @Column(name = "table_align")
    private String tableAlign;

    @Column(name = "grand_total_align")
    private String grandTotalAlign;

    public ClientLayoutPreferences() {
    }

    public ClientLayoutPreferences(String clientName, String companyNameAlign, String addressAlign, String dateAlign, String customerNameAlign, String salesInvoiceAlign, String tableAlign, String grandTotalAlign) {
        this.clientName = clientName;
        this.companyNameAlign = companyNameAlign;
        this.addressAlign = addressAlign;
        this.dateAlign = dateAlign;
        this.customerNameAlign = customerNameAlign;
        this.salesInvoiceAlign = salesInvoiceAlign;
        this.tableAlign = tableAlign;
        this.grandTotalAlign = grandTotalAlign;
    }

    
    

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCompanyNameAlign() {
        return companyNameAlign;
    }

    public void setCompanyNameAlign(String companyNameAlign) {
        this.companyNameAlign = companyNameAlign;
    }

    public String getAddressAlign() {
        return addressAlign;
    }

    public void setAddressAlign(String addressAlign) {
        this.addressAlign = addressAlign;
    }

    public String getDateAlign() {
        return dateAlign;
    }

    public void setDateAlign(String dateAlign) {
        this.dateAlign = dateAlign;
    }

    public String getCustomerNameAlign() {
        return customerNameAlign;
    }

    public void setCustomerNameAlign(String customerNameAlign) {
        this.customerNameAlign = customerNameAlign;
    }

    public String getSalesInvoiceAlign() {
        return salesInvoiceAlign;
    }

    public void setSalesInvoiceAlign(String salesInvoiceAlign) {
        this.salesInvoiceAlign = salesInvoiceAlign;
    }

    public String getTableAlign() {
        return tableAlign;
    }

    public void setTableAlign(String tableAlign) {
        this.tableAlign = tableAlign;
    }

    public String getGrandTotalAlign() {
        return grandTotalAlign;
    }

    public void setGrandTotalAlign(String grandTotalAlign) {
        this.grandTotalAlign = grandTotalAlign;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.clientName);
        hash = 37 * hash + Objects.hashCode(this.companyNameAlign);
        hash = 37 * hash + Objects.hashCode(this.addressAlign);
        hash = 37 * hash + Objects.hashCode(this.dateAlign);
        hash = 37 * hash + Objects.hashCode(this.customerNameAlign);
        hash = 37 * hash + Objects.hashCode(this.salesInvoiceAlign);
        hash = 37 * hash + Objects.hashCode(this.tableAlign);
        hash = 37 * hash + Objects.hashCode(this.grandTotalAlign);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClientLayoutPreferences other = (ClientLayoutPreferences) obj;
        if (!Objects.equals(this.clientName, other.clientName)) {
            return false;
        }
        if (!Objects.equals(this.companyNameAlign, other.companyNameAlign)) {
            return false;
        }
        if (!Objects.equals(this.addressAlign, other.addressAlign)) {
            return false;
        }
        if (!Objects.equals(this.dateAlign, other.dateAlign)) {
            return false;
        }
        if (!Objects.equals(this.customerNameAlign, other.customerNameAlign)) {
            return false;
        }
        if (!Objects.equals(this.salesInvoiceAlign, other.salesInvoiceAlign)) {
            return false;
        }
        if (!Objects.equals(this.tableAlign, other.tableAlign)) {
            return false;
        }
        return Objects.equals(this.grandTotalAlign, other.grandTotalAlign);
    }
    
    
}

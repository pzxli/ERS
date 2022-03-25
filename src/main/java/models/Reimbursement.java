package models;

import java.util.Date;

public class Reimbursement {
    private Integer id;
    private Double amount;
    private Date submitted;
    private Date resolved;
    private String description;
    private Integer author;
    private Integer resolver;
    private Integer statusId;
    private Integer typeId;

    public Reimbursement(){
    }

    public Reimbursement(Integer resolver, Integer statusId, Integer id) {
        this.resolver = resolver;
        this.statusId = statusId;
        this.id = id;
    }

    public Reimbursement(Double amount, String description, Integer author, Integer typeId) {
        this.amount = amount;
        this.description = description;
        this.author = author;
        this.typeId = typeId;
    }

    public Reimbursement(Integer id, Double amount, Date submitted, Date resolved, String description, Integer author, Integer resolver, Integer statusId, Integer typeId) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.author = author;
        this.resolver = resolver;
        this.statusId = statusId;
        this.typeId = typeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date submitted) {
        this.submitted = submitted;
    }

    public Date getResolved() {
        return resolved;
    }

    public void setResolved(Date resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getResolver() {
        return resolver;
    }

    public void setResolver(Integer resolver) {
        this.resolver = resolver;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "Id=" + id +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", resolver=" + resolver +
                ", statusId=" + statusId +
                ", typeId=" + typeId +
                '}';
    }
}

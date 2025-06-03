package models;

import java.util.Arrays;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonAlias;

public class Reimbursement {
    private Integer id;
    private Double amount;
    private Date submitted;
    private Date resolved;
    private String description;
    private byte[] receipt;
    private Integer author;
    @JsonAlias("resolverId")
    private Integer resolver;
    @JsonAlias("statusId")
    private Integer statusId;
    private Integer typeId;

    private String status;
    private String type;

    private String authorFullName;
    private String resolverFullName;

    public Reimbursement() {
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

    public Reimbursement(Integer id, Double amount, Date submitted, Date resolved, String description, byte[] receipt, Integer author, Integer resolver, Integer statusId, Integer typeId) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.author = author;
        this.resolver = resolver;
        this.statusId = statusId;
        this.typeId = typeId;
    }

    // New constructor with readable status/type
    public Reimbursement(Integer id, Double amount, Date submitted, Date resolved, String description, byte[] receipt, Integer author, Integer resolver, String status, String type) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.author = author;
        this.resolver = resolver;
        this.status = status;
        this.type = type;
    }

    // Getters and Setters

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

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public void setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
    }

    public String getResolverFullName() {
        return resolverFullName;
    }

    public void setResolverFullName(String resolverFullName) {
        this.resolverFullName = resolverFullName;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receipt=" + Arrays.toString(receipt) +
                ", author=" + author +
                ", resolver=" + resolver +
                ", statusId=" + statusId +
                ", typeId=" + typeId +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", authorFullName='" + authorFullName + '\'' +
                ", resolverFullName='" + resolverFullName + '\'' +
                '}';
    }
}

package models;

public class ReimbursementStatus {
    private Integer id;
    private String status;

    public ReimbursementStatus() {
    }

    public ReimbursementStatus(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reimbursement_Status{" +
                "Id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}

package models;

public class ReimbursementType {
    private Integer id;
    private String type;

    public ReimbursementType() {
    }

    public ReimbursementType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Reimbursement_Type{" +
                "Id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}

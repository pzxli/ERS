package models;

public class UserRole {
    private Integer id;
    private String roleName;

    public UserRole() {
    }

    public UserRole(Integer id, String role) {
        this.id = id;
        this.roleName = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "User_Role{" +
                "Id=" + id +
                ", role='" + roleName + '\'' +
                '}';
    }
}

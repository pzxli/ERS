package repositories;

import models.Reimbursement;
import models.User;
import util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public User getUserGivenUsername(String username) {
        User user = null;

        try (Connection conn = ConnectionUtil.getConnection()) {

            String sql = "SELECT * FROM ERS_USERS WHERE ERS_USERNAME = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("ERS_USERS_ID"));
                user.setUsername(rs.getString("ERS_USERNAME"));
                user.setPassword(rs.getString("ERS_PASSWORD"));
                user.setRole(rs.getString("USER_ROLE"));
                user.setFirstname(rs.getString("USER_FIRST_NAME"));
                user.setLastname(rs.getString("USER_LAST_NAME"));
                user.setEmail(rs.getString("USER_EMAIL"));
                user.setRoleId(rs.getInt("USER_ROLE_ID_FK"));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return user;
    }


    @Override
    public void createUser(User user) {
        try (Connection conn = ConnectionUtil.getConnection()) {

            String sql = "INSERT INTO ERS_USERS(ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME , USER_LAST_NAME, USER_EMAIL) VALUES (?, ?, ?, ?, ?);";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstname());
            ps.setString(4, user.getLastname());
            ps.setString(5, user.getEmail());

            ps.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
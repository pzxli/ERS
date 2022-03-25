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

            //preparing our sql statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //we are adding the username into the question mark in the sql statement
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
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
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getEmail());

            ps.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
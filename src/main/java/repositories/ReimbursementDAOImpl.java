package repositories;

import models.Reimbursement;
import util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReimbursementDAOImpl implements ReimbursementDAO{

    @Override
    public void submitReimbursement(Reimbursement reimbursement) {
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO REIMBURSEMENT(REIMB_AMOUNT, REIMB_DESCRIPTION, REIMB_AUTHOR_FK, REIMB_TYPE_ID_FK) VALUES (?, ?, ?, ?);";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, reimbursement.getAmount());
            ps.setString(2, reimbursement.getDescription());
            ps.setInt(3, reimbursement.getAuthor());
            ps.setInt(4, reimbursement.getTypeId());

            ps.executeQuery();

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    @Override
    public List<Reimbursement> viewPastReimbursements(Integer author) {
        List<Reimbursement> lists = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM REIMBURSEMENT WHERE REIMB_AUTHOR_FK = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, author);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lists.add(new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getDate(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
            }

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return lists;
    }

    @Override
    public List<Reimbursement> getAllReimbursements() {
        List<Reimbursement> lists = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM REIMBURSEMENT;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                lists.add(new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getDate(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
            }

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }

        return lists;
    }

    @Override
    public void updateReimbursement(Reimbursement reimbursement) {
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE REIMBURSEMENT SET REIMB_RESOLVED = current_timestamp, REIMB_RESOLVER_FK = ?, REIMB_STATUS_ID_FK = ? WHERE REIMB_ID = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reimbursement.getResolver());
            ps.setInt(2, reimbursement.getStatusId());
            ps.setInt(3, reimbursement.getId());

            ps.executeUpdate();

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    @Override
    public List<Reimbursement> filterByStatus(Integer statusId) {
        List<Reimbursement> lists = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM REIMBURSEMENT WHERE REIMB_STATUS_ID_FK = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, statusId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                lists.add(new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getDate(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
            }

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }

        return lists;
    }


}

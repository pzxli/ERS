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
            String sql = "INSERT INTO ERS_REIMBURSEMENT(REIMB_AMOUNT, REIMB_DESCRIPTION, REIMB_AUTHOR_FK, REIMB_TYPE_ID_FK) VALUES (?, ?, ?, ?);";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, reimbursement.getAmount());
            ps.setString(2, reimbursement.getDescription());
            ps.setInt(3, reimbursement.getAuthor());
            ps.setInt(4, reimbursement.getTypeId());

            ps.executeUpdate();

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    @Override
    public List<Reimbursement> viewPastReimbursements(Integer author) {
        List<Reimbursement> lists = new ArrayList<>();

        String sql = "SELECT ERS_REIMBURSEMENT.REIMB_ID, " +
                "ERS_REIMBURSEMENT.REIMB_AMOUNT, " +
                "ERS_REIMBURSEMENT.REIMB_SUBMITTED, " +
                "ERS_REIMBURSEMENT.REIMB_RESOLVED, " +
                "ERS_REIMBURSEMENT.REIMB_DESCRIPTION, " +
                "ERS_REIMBURSEMENT.REIMB_RECEIPT, " +
                "ERS_REIMBURSEMENT.REIMB_AUTHOR_FK, " +
                "ERS_REIMBURSEMENT.REIMB_RESOLVER_FK, " +
                "ERS_REIMBURSEMENT_STATUS.REIMB_STATUS, " +
                "ERS_REIMBURSEMENT_TYPE.REIMB_TYPE " +
                "FROM ERS_REIMBURSEMENT " +
                "JOIN ERS_REIMBURSEMENT_STATUS ON ERS_REIMBURSEMENT.REIMB_STATUS_ID_FK = ERS_REIMBURSEMENT_STATUS.REIMB_STATUS_ID " +
                "JOIN ERS_REIMBURSEMENT_TYPE ON ERS_REIMBURSEMENT.REIMB_TYPE_ID_FK = ERS_REIMBURSEMENT_TYPE.REIMB_TYPE_ID " +
                "WHERE ERS_REIMBURSEMENT.REIMB_AUTHOR_FK = ?;";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, author);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reimbursement r = new Reimbursement();
                r.setId(rs.getInt("REIMB_ID"));
                r.setAmount(rs.getDouble("REIMB_AMOUNT"));
                r.setSubmitted(rs.getTimestamp("REIMB_SUBMITTED"));
                r.setResolved(rs.getTimestamp("REIMB_RESOLVED"));
                r.setDescription(rs.getString("REIMB_DESCRIPTION"));
                r.setReceipt(rs.getBytes("REIMB_RECEIPT"));
                r.setAuthor(rs.getInt("REIMB_AUTHOR_FK"));
                r.setResolver(rs.getInt("REIMB_RESOLVER_FK"));
                r.setStatus(rs.getString("REIMB_STATUS"));  // e.g. "Pending"
                r.setType(rs.getString("REIMB_TYPE"));      // e.g. "Lodging"

                lists.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lists;
    }


    @Override
    public List<Reimbursement> getAllReimbursements() {
        List<Reimbursement> lists = new ArrayList<>();

        String sql =
                "SELECT " +
                        "  ERS_REIMBURSEMENT.REIMB_ID, " +
                        "  ERS_REIMBURSEMENT.REIMB_AMOUNT, " +
                        "  ERS_REIMBURSEMENT.REIMB_SUBMITTED, " +
                        "  ERS_REIMBURSEMENT.REIMB_RESOLVED, " +
                        "  ERS_REIMBURSEMENT.REIMB_DESCRIPTION, " +
                        "  ERS_REIMBURSEMENT.REIMB_RECEIPT, " +
                        "  ERS_REIMBURSEMENT.REIMB_AUTHOR_FK, " +
                        "  ERS_REIMBURSEMENT.REIMB_RESOLVER_FK, " +
                        "  ERS_REIMBURSEMENT_STATUS.REIMB_STATUS, " +
                        "  ERS_REIMBURSEMENT_TYPE.REIMB_TYPE, " +
                        "  AUTHOR.USER_FIRST_NAME AS AUTHOR_FIRST, " +
                        "  AUTHOR.USER_LAST_NAME AS AUTHOR_LAST, " +
                        "  RESOLVER.USER_FIRST_NAME AS RESOLVER_FIRST, " +
                        "  RESOLVER.USER_LAST_NAME AS RESOLVER_LAST " +
                        "FROM ERS_REIMBURSEMENT " +
                        "JOIN ERS_REIMBURSEMENT_STATUS ON ERS_REIMBURSEMENT.REIMB_STATUS_ID_FK = ERS_REIMBURSEMENT_STATUS.REIMB_STATUS_ID " +
                        "JOIN ERS_REIMBURSEMENT_TYPE ON ERS_REIMBURSEMENT.REIMB_TYPE_ID_FK = ERS_REIMBURSEMENT_TYPE.REIMB_TYPE_ID " +
                        "JOIN ERS_USERS AUTHOR ON ERS_REIMBURSEMENT.REIMB_AUTHOR_FK = AUTHOR.ERS_USERS_ID " +
                        "LEFT JOIN ERS_USERS RESOLVER ON ERS_REIMBURSEMENT.REIMB_RESOLVER_FK = RESOLVER.ERS_USERS_ID " +
                        "ORDER BY ERS_REIMBURSEMENT.REIMB_SUBMITTED ASC";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reimbursement r = new Reimbursement();
                r.setId(rs.getInt("REIMB_ID"));
                r.setAmount(rs.getDouble("REIMB_AMOUNT"));
                r.setSubmitted(rs.getTimestamp("REIMB_SUBMITTED"));
                r.setResolved(rs.getTimestamp("REIMB_RESOLVED"));
                r.setDescription(rs.getString("REIMB_DESCRIPTION"));
                r.setReceipt(rs.getBytes("REIMB_RECEIPT"));
                r.setAuthor(rs.getInt("REIMB_AUTHOR_FK"));
                r.setResolver(rs.getInt("REIMB_RESOLVER_FK"));
                r.setStatus(rs.getString("REIMB_STATUS"));
                r.setType(rs.getString("REIMB_TYPE"));

                String authorName = rs.getString("author_first") + " " + rs.getString("author_last");
                r.setAuthorFullName(authorName);

                String resolverFirst = rs.getString("resolver_first");
                String resolverLast = rs.getString("resolver_last");
                if (resolverFirst != null && resolverLast != null) {
                    r.setResolverFullName(resolverFirst + " " + resolverLast);
                }

                lists.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lists;
    }

    @Override
    public void updateReimbursement(Reimbursement reimbursement) {
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE ERS_REIMBURSEMENT SET REIMB_RESOLVED = current_timestamp, REIMB_RESOLVER_FK = ?, REIMB_STATUS_ID_FK = ? WHERE REIMB_ID = ?;";

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

        String sql =
                "SELECT " +
                        "ERS_REIMBURSEMENT.REIMB_ID, " +
                        "ERS_REIMBURSEMENT.REIMB_AMOUNT, " +
                        "ERS_REIMBURSEMENT.REIMB_SUBMITTED, " +
                        "ERS_REIMBURSEMENT.REIMB_RESOLVED, " +
                        "ERS_REIMBURSEMENT.REIMB_DESCRIPTION, " +
                        "ERS_REIMBURSEMENT.REIMB_RECEIPT, " +
                        "ERS_REIMBURSEMENT.REIMB_AUTHOR_FK, " +
                        "ERS_REIMBURSEMENT.REIMB_RESOLVER_FK, " +
                        "ERS_REIMBURSEMENT_STATUS.REIMB_STATUS, " +
                        "ERS_REIMBURSEMENT_TYPE.REIMB_TYPE, " +
                        "AUTHOR.USER_FIRST_NAME AS AUTHOR_FIRST, " +
                        "AUTHOR.USER_LAST_NAME AS AUTHOR_LAST, " +
                        "RESOLVER.USER_FIRST_NAME AS RESOLVER_FIRST, " +
                        "RESOLVER.USER_LAST_NAME AS RESOLVER_LAST " +
                        "FROM ERS_REIMBURSEMENT " +
                        "JOIN ERS_REIMBURSEMENT_STATUS ON ERS_REIMBURSEMENT.REIMB_STATUS_ID_FK = ERS_REIMBURSEMENT_STATUS.REIMB_STATUS_ID " +
                        "JOIN ERS_REIMBURSEMENT_TYPE ON ERS_REIMBURSEMENT.REIMB_TYPE_ID_FK = ERS_REIMBURSEMENT_TYPE.REIMB_TYPE_ID " +
                        "JOIN ERS_USERS AUTHOR ON ERS_REIMBURSEMENT.REIMB_AUTHOR_FK = AUTHOR.ERS_USERS_ID " +
                        "LEFT JOIN ERS_USERS RESOLVER ON ERS_REIMBURSEMENT.REIMB_RESOLVER_FK = RESOLVER.ERS_USERS_ID " +
                        "WHERE ERS_REIMBURSEMENT.REIMB_STATUS_ID_FK = ? " +
                        "ORDER BY ERS_REIMBURSEMENT.REIMB_SUBMITTED ASC";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, statusId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reimbursement r = new Reimbursement();
                r.setId(rs.getInt("REIMB_ID"));
                r.setAmount(rs.getDouble("REIMB_AMOUNT"));
                r.setSubmitted(rs.getTimestamp("REIMB_SUBMITTED"));
                r.setResolved(rs.getTimestamp("REIMB_RESOLVED"));
                r.setDescription(rs.getString("REIMB_DESCRIPTION"));
                r.setReceipt(rs.getBytes("REIMB_RECEIPT"));
                r.setAuthor(rs.getInt("REIMB_AUTHOR_FK"));
                r.setResolver(rs.getInt("REIMB_RESOLVER_FK"));
                r.setStatus(rs.getString("REIMB_STATUS"));
                r.setType(rs.getString("REIMB_TYPE"));

                String authorName = rs.getString("author_first") + " " + rs.getString("author_last");
                r.setAuthorFullName(authorName);

                String resolverFirst = rs.getString("resolver_first");
                String resolverLast = rs.getString("resolver_last");
                if (resolverFirst != null && resolverLast != null) {
                    r.setResolverFullName(resolverFirst + " " + resolverLast);
                }

                lists.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lists;
    }



}

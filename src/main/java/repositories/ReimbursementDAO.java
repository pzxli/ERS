package repositories;

import models.Reimbursement;

import java.util.List;

public interface ReimbursementDAO {

    //submit reimbursement
    void submitReimbursement(Reimbursement reimbursement);

    //view past tickets
    List<Reimbursement> viewPastReimbursements(Integer author);

    //view all reimbursements for all employees
    List<Reimbursement> getAllReimbursements();

    //approve or deny reimbursements
    void updateReimbursement(Reimbursement reimbursement);

    //filter reimbursements by status
    List<Reimbursement> filterByStatus(Integer statusId);


}

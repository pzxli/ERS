package services;

import models.Reimbursement;
import repositories.ReimbursementDAO;
import repositories.ReimbursementDAOImpl;

import java.util.List;

public class ReimbursementService {
    private ReimbursementDAO reimbursementDAO;

    public ReimbursementService(){
        this.reimbursementDAO = new ReimbursementDAOImpl();
    }

    public ReimbursementService(ReimbursementDAO reimbursementDAO){
        this.reimbursementDAO = reimbursementDAO;
    }

    public void submitReimbursement(Reimbursement reimbursement){
        this.reimbursementDAO.submitReimbursement(reimbursement);
    }

    public List<Reimbursement> viewPastReimbursements(Integer author){
        return this.reimbursementDAO.viewPastReimbursements(author);
    }

    public List<Reimbursement> getAllReimbursements(){
        return this.reimbursementDAO.getAllReimbursements();
    }

    public void updateReimbursement(Reimbursement reimbursement){
        this.reimbursementDAO.updateReimbursement(reimbursement);
    }

    public List<Reimbursement> filterByStatus(Integer statusId){
        return this.reimbursementDAO.filterByStatus(statusId);
    }

}

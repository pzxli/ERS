package services;

import models.Reimbursement;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repositories.ReimbursementDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementServiceTest {

    ReimbursementService reimbursementService;

    ReimbursementDAO reimbursementDAO = Mockito.mock(ReimbursementDAO.class);

    public ReimbursementServiceTest(){
        reimbursementService = new ReimbursementService(reimbursementDAO);
    }

    @Test
    void submitReimbursement() {
        // arrange
        // Use the constructor Reimbursement(Double amount, String description, Integer author, Integer TypeId)
        Reimbursement reimbursementToPass = new Reimbursement(250.00, "gas money", 1, 3);

        // act
        reimbursementService.submitReimbursement(reimbursementToPass);

        // assert
        Mockito.verify(reimbursementDAO, Mockito.times(1)).submitReimbursement(reimbursementToPass);
    }

    @Test
    void viewPastReimbursements() {
        // arrange
        Integer author = 2;
        List<Reimbursement> expectedOutput = new ArrayList<>();

        // Use the constructor Reimbursement(Integer id, Double amount, Date submitted, Date resolved, String description, byte[] receipt, Integer author, Integer resolver, Integer statusId, Integer typeId)
        expectedOutput.add(new Reimbursement(1, 200.0, null, null, "gas money", null, author, 3, 1, 3));
        Mockito.when(reimbursementDAO.viewPastReimbursements(author)).thenReturn(expectedOutput);

        // act
        List<Reimbursement> actualOutput = reimbursementService.viewPastReimbursements(author);

        // assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllReimbursements() {
        // arrange
        List<Reimbursement> expectedOutput = new ArrayList<>();
        expectedOutput.add(new Reimbursement(1, 200.0, null, null, "gas money", null, 2, 3, 1, 3));
        expectedOutput.add(new Reimbursement(2, 250.0, null, null, "food money", null, 2, 3, 2, 4));
        Mockito.when(reimbursementDAO.getAllReimbursements()).thenReturn(expectedOutput);

        // act
        List<Reimbursement> actualOutput = reimbursementService.getAllReimbursements();

        // assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void approveOrDeny() {
        // arrange
        // Use the constructor: Reimbursement(Integer resolver, Integer statusId, Integer id)
        Reimbursement approveTest = new Reimbursement(3, 3, 3);

        // act
        reimbursementService.updateReimbursement(approveTest);

        // assert
        Mockito.verify(reimbursementDAO, Mockito.times(1)).updateReimbursement(approveTest);
    }

    @Test
    void filterByStatus() {
        // arrange
        Integer statusId = 3;
        List<Reimbursement> expectedOutput = new ArrayList<>();
        expectedOutput.add(new Reimbursement(1, 200.0, null, null, "gas money", null, 1, 3, statusId, 4));
        Mockito.when(reimbursementDAO.filterByStatus(statusId)).thenReturn(expectedOutput);

        // act
        List<Reimbursement> actualOutput = reimbursementService.filterByStatus(statusId);

        // assert
        assertEquals(expectedOutput, actualOutput);
    }
}

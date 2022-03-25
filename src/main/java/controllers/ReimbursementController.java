package controllers;

import io.javalin.http.Context;
import models.JsonResponse;
import models.Reimbursement;
import services.ReimbursementService;

import java.util.List;

public class ReimbursementController {
    ReimbursementService reimbursementService;

    public ReimbursementController(){
        this.reimbursementService = new ReimbursementService();
    }

    public ReimbursementController(ReimbursementService reimbursementService){
        this.reimbursementService = reimbursementService;
    }

    public void submitReimbursement(Context context){
        Reimbursement reimbursement = context.bodyAsClass(Reimbursement.class);

        reimbursementService.submitReimbursement(reimbursement);

        context.json(new JsonResponse(true, "reimbursement created", null));

    }

    public void displayPastReimbursements(Context context){
        Integer author = Integer.parseInt(context.pathParam("author"));

        List<Reimbursement> reimbursementList = reimbursementService.viewPastReimbursements(author);
        context.json(new JsonResponse(true, "displaying all past reimbursements for user " + author, reimbursementList));
    }

    public void displayAllReimbursements(Context context){

        List<Reimbursement> reimbursementList = reimbursementService.getAllReimbursements();
        context.json(new JsonResponse(true, "displaying all reimbursement requests", reimbursementList));
    }

    public void updateReimbursement(Context context){
        Reimbursement reimbursement = context.bodyAsClass(Reimbursement.class);

        reimbursementService.updateReimbursement(reimbursement);

        context.json(new JsonResponse(true, "reimbursement has been updated", null));
    }

    public void filterByStatus(Context context){
        Integer statusId = Integer.parseInt(context.pathParam("statusId"));

        List<Reimbursement> reimbursementList = reimbursementService.filterByStatus(statusId);
        context.json(new JsonResponse(true, "displaying all reimbursements where statusId = " + statusId, reimbursementList));
    }

}

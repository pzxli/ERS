import controllers.ReimbursementController;
import controllers.SessionController;
import controllers.UserController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import models.Reimbursement;
import models.User;
import repositories.ReimbursementDAO;
import repositories.ReimbursementDAOImpl;
import repositories.UserDAO;
import repositories.UserDAOImpl;

public class MainDriver {
    public static void main(String[] args) {

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.addStaticFiles("/", Location.CLASSPATH);
        }).start(9001);

//        Javalin app = Javalin.create().start(9001);

        UserController userController = new UserController();
        ReimbursementController reimbursementController = new ReimbursementController();
        SessionController sessionController = new SessionController();


        //user endpoints
        app.post("/user", userController::createUser);
        app.post("/session", sessionController::login);
        app.get("/session", sessionController::checkSession);
        app.delete("/session", sessionController::logout);

        //employee endpoints
        app.get("/user/{author}/list", reimbursementController::displayPastReimbursements);
        app.post("/user/{author}/list", reimbursementController::submitReimbursement);

        //finance manager endpoints
        app.get("/user/{author}/all", reimbursementController::displayAllReimbursements);
        app.patch("/user/{id}/list", reimbursementController::updateReimbursement);
        app.get("/user/{statusId}/list", reimbursementController::filterByStatus);


        //works --

        //create user
        //UserDAO userDAO = new UserDAOImpl();
        //userDAO.createUser(new User("username1234", "pass1234", "first2", "last2", "email1234@gmail.com"));

        //get user
        //UserDAO userDAO = new UserDAOImpl();
        //System.out.println(userDAO.getUserGivenUsername("username123"));

        //creating new reimbursement
        //UserDAO userDAO = new UserDAOImpl();
        //userDAO.submitReimbursement(new Reimbursement(2000.00, "winter break", 1, 4));

        //view past reimbursements
        //ReimbursementDAO reimbursementDAO = new ReimbursementDAOImpl();
        //System.out.println(reimbursementDAO.viewPastReimbursements(1));

        //view all reimbursements
        //System.out.println(reimbursementDAO.getAllReimbursements());

        //approve or deny
        //ReimbursementDAO reimbursementDAO = new ReimbursementDAOImpl();
        //reimbursementDAO.approveOrDeny(3, 3, 3);

        //filter by status
        //ReimbursementDAO reimbursementDAO = new ReimbursementDAOImpl();
        //System.out.println(reimbursementDAO.filterByStatus(2));

    }
}

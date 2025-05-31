package controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import models.JsonResponse;
import models.User;
import org.eclipse.jetty.server.Authentication;
import services.UserService;

public class UserController {
    private UserService userService;

    public UserController(){
        this.userService = new UserService();
    }

    public UserController(UserService userService){
        this.userService = userService;
    }

    public void createUser(Context context){
        JsonResponse jsonResponse;
        User user = context.bodyAsClass(User.class);
        if(userService.createUser(user)){
            jsonResponse = new JsonResponse(true, "user has been created", null);
        }else{
            jsonResponse = new JsonResponse(false, "username already exist", null);
        }
        context.json(jsonResponse);
    }

}
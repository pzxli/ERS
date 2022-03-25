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

    public void login(Context context){
        JsonResponse jsonResponse;

        User credentials = context.bodyAsClass(User.class);

        User userFromDb = userService.validateCredentials(credentials.getUsername(), credentials.getPassword());

        if(userFromDb == null){
            jsonResponse = new JsonResponse(false, "invalid username or password", null);
        }else{
            jsonResponse = new JsonResponse(true, "login successful", userFromDb);
        }

        context.json(jsonResponse);
    }


}
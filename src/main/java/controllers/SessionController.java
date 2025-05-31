package controllers;

import io.javalin.http.Context;
import models.JsonResponse;
import models.User;
import services.UserService;

public class SessionController {
    private UserService userService;

    public SessionController(){this.userService = new UserService(); }

    public void login(Context context){
        JsonResponse jsonResponse;

        User credentials = context.bodyAsClass(User.class);

        User userFromDb = userService.validateCredentials(credentials.getUsername(), credentials.getPassword());

        if(userFromDb == null){
            jsonResponse = new JsonResponse(false, "invalid username or password", null);
        }else{
            context.sessionAttribute("user", userFromDb);
            jsonResponse = new JsonResponse(true, "login successful", userFromDb);
        }

        context.json(jsonResponse);
    }

    public void checkSession(Context context){
        User user = context.sessionAttribute("user");

        if(user == null){
            context.json(new JsonResponse(false, "no session found", null));
        }else{
            context.json(new JsonResponse(true, "session found", user));
        }

    }

    public void logout(Context context){
        context.sessionAttribute("user", null);

        context.json(new JsonResponse(true, "session invalidated", null));
    }

}

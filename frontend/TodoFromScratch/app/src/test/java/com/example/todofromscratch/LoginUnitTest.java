package com.example.todofromscratch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.todofromscratch.model.net.ServerFacade;
import com.example.todofromscratch.model.net.TweeterRemoteException;
import com.example.todofromscratch.model.net.request.LoginRequest;
import com.example.todofromscratch.model.net.request.RegisterRequest;
import com.example.todofromscratch.model.net.response.LoginResponse;
import com.example.todofromscratch.model.net.response.RegisterResponse;
import com.example.todofromscratch.model.service.UserService;

import org.junit.Test;

import java.io.IOException;

public class LoginUnitTest {
    /**
     * Make sure you run TestRegister before test login so the user is created in the database
     */
    @Test
    public void testRegister(){
        String username = "testUser";
        String password = "TerriblePassword";
        RegisterRequest request = new RegisterRequest(username, password, "hhh@gmail.com", "Steve", "Minecraft");
        ServerFacade serverFacade = new ServerFacade();
        try {
            RegisterResponse response = serverFacade.register(request, UserService.REGISTER_URL_PATH);
            assertTrue(response.isSuccess());
            assertEquals(response.getUser().getUsername(), username);
        } catch (IOException | TweeterRemoteException e) {
            throw new RuntimeException(e);
        }


    }
    @Test
    public void testLogin() {
        String username = "testUser";
        String password = "TerriblePassword";
        LoginRequest request = new LoginRequest(username, password);
        ServerFacade serverFacade = new ServerFacade();
        try{
            LoginResponse response = serverFacade.login(request, UserService.URL_PATH);
            assertTrue(response.isSuccess());
            assertNotNull(response.getAuthtoken());
        } catch (IOException | TweeterRemoteException e) {
            throw new RuntimeException(e);
        }
    }

}

package com.org.SpringSecurityAuth.controller;


import org.apache.catalina.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.Map;

@RestController
public class LoginController {

    //Cette déclaration permet de stocker le token de façon sécurisée et immuable.
    private final OAuth2AuthorizedClientService authorizedClientService;

    @RequestMapping("/**")
    @RolesAllowed("USERS")
    public String getUser(){
        return "Welcome User" ;
    }

    @RequestMapping("/admin")
    @RolesAllowed({"ADMIN","USERS"})
    public String getAdmin(){
        return "Welcome Admin";
    }
    @RequestMapping("/*")
    public String getUserInfos(Principal user) {
        StringBuffer userInfo = new StringBuffer();
        if(user instanceof UsernamePasswordAuthenticationToken){
            userInfo.append(getUsernamePasswordLoginInfo(user));
        }
        else if(user instanceof OAuth2AuthenticationToken){
            userInfo.append(getOauth2LoginInfo(user));
        }

        return userInfo.toString();


    }
    private StringBuffer getUsernamePasswordLoginInfo(Principal user){
        StringBuffer userNameInfo = new StringBuffer();

        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken)user);
        if(token.isAuthenticated()){
            User u = (User) token.getPrincipal();
            userNameInfo.append("Welcome"+ u.getUsername());

        }else {
            userNameInfo.append("NA");
        }
        return userNameInfo;

    }
    public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }
    private StringBuffer getOauth2LoginInfo(Principal user){
        StringBuffer protectedInfo = new StringBuffer();
        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
        OAuth2AuthorizedClient authClient = this.authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
        if(authToken.isAuthenticated()){

            Map<String,Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();

            String userToken = authClient.getAccessToken().getTokenValue();
            protectedInfo.append("Welcome, " + userAttributes.get("name")+"<br><br>");
            protectedInfo.append("e-mail: " + userAttributes.get("email")+"<br><br>");
           // protectedInfo.append("Access Token: " + userToken+"<br><br>"); // Dans le cas où on ne veut pas faire figurer le token d'acces dans l'interface.
        }
        else{
            protectedInfo.append("NA");
        }
        return protectedInfo;
    }

}

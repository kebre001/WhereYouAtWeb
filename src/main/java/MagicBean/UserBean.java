/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MagicBean;

import Model.UserModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseUser;

/**
 *
 * @author Kebre
 */

@ManagedBean
@SessionScoped
public class UserBean {
    private String objectId;
    private String username;
    private String password;
    //private authData authData;
    private Boolean emailVerified;
    private String email;
    private String firstname;
    private String lastname;
    private String profilePictureID;
    private Date createdAt;
    private Date updatedAt;
    //private ACL ACL;
    
    private UserModel userModel = null;
    
    private List<UserModel> userList = new ArrayList<UserModel>();
    
    private UserModel userProfile = null;
    String APP_ID = "wMNwNpfj87bX3Dia3mDqXiczJkmay2q9xNN7Fa6s";
    String APP_REST_API_ID = "wHyn6HLqZIfiOkhrLsqCg8poip73iG3xLsel3gAz";
    
    
    
    public UserBean() {
        UserModel userM = new UserModel();
        userM.setUsername("");
        userList.add(userM);
        userModel = new UserModel();
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProfilePictureID() {
        return profilePictureID;
    }

    public void setProfilePictureID(String profilePictureID) {
        this.profilePictureID = profilePictureID;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<UserModel> getUserList() {
        return userList;
    }

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
    }

    public UserModel getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserModel userProfile) {
        this.userProfile = userProfile;
    }
    
    
    
    public String registerUser(){
        String registerSuccess="";
        Parse.initialize(APP_ID, APP_REST_API_ID);
        
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("firstname", firstname);
        user.put("lastname", lastname);
        
        try {
            user.signUp();
            userModel.setEmail(email);
            userModel.setFirstname(firstname);
            userModel.setLastname(lastname);
            userModel.setPassword(password);
            userModel.setUsername(username);
            registerSuccess = "true";
        } catch (ParseException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            registerSuccess = "false";
        }
        
        if(registerSuccess.equalsIgnoreCase("true")){
            return "index";
        }else{
            return "register";
        }
    }
    
    public String loginUser(){
        ParseUser user = new ParseUser();
        try {
            user = ParseUser.logIn(username, password);
            userModel.setEmail((String) user.get("email"));
            userModel.setFirstname((String) user.get("firstname"));
            userModel.setLastname((String) user.get("lastname"));
            userModel.setPassword((String) user.get("password"));
            userModel.setUsername((String) user.get("username"));
        } catch (ParseException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(userModel.getUsername() != null){
            return "welcomePrimefaces";
        }else{
            return "login";
        }
    }
    
    public void checkLogin(ComponentSystemEvent event){
        FacesContext fc = FacesContext.getCurrentInstance();
        
        if(userModel.getUsername() == "" || userModel.getUsername() == null){
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) 
            fc.getApplication().getNavigationHandler();
            nav.performNavigation("login");
        }
    }
    
    public String logout(){
        userModel = new UserModel();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }
    
    public void clearBean(){
        username = "";
        password = "";
        firstname = "";
        lastname = "";
        email = "";
    }
}

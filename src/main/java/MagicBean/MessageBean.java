/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MagicBean;

import Model.MessageModel;
import java.io.Serializable;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseUser;
import org.parse4j.callback.FindCallback;

/**
 *
 * @author Kebre
 */
@ManagedBean
@SessionScoped
public class MessageBean implements Serializable{
    private String objectId;
    private String message;
    private String receiver;
    private String sender;
    private Date createdAt;
    
    private boolean userFound;
    
    private int limit;
    private List<MessageModel> messageList = new ArrayList<MessageModel>();
    
    String APP_ID = "wMNwNpfj87bX3Dia3mDqXiczJkmay2q9xNN7Fa6s";
    String APP_REST_API_ID = "wHyn6HLqZIfiOkhrLsqCg8poip73iG3xLsel3gAz";

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    
    
        public List<MessageModel> fetchMessages(){
        System.out.println("FETCHING MESSAGES");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Messages");
        query.whereEqualTo("from", sender);
        query.whereEqualTo("to", receiver);
        query.limit(limit);
        
        messageList = new ArrayList<MessageModel>();
        
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> msgList, ParseException e) {
                if (e == null) {
                    //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    for(int i=0; i<messageList.size(); i++){
                        
                        MessageModel tempMessage = new MessageModel();
                        
                        tempMessage.setSender(msgList.get(i).getString("sender"));
                        tempMessage.setReceiver(msgList.get(i).getString("receiver"));
                        tempMessage.setMessage(msgList.get(i).getString("message"));
                        
                        messageList.add(tempMessage);
                        System.out.println(tempMessage);
                        //System.out.println("Message: " + from + "<:>" + to + "<:>" + message);
                    }
                } else {
                    //Log.d("score", "Error: " + e.getMessage());
                    System.out.println("ERROR IN FETCHING MSGS");
                }
            }
        });
        return messageList;
    }
    public String startChat(){
        checkReceiver();
        if(true){
            return "chat";
        }else{
            return "findChat";
        }
    }
        
    public void checkReceiver(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("email", receiver);
        System.out.println("RUNNING QUERY WITH THIS: " + receiver);

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                System.out.println("FOUND THIS IN QUERY: " + userList);
                if (e == null) {
                    //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    System.out.println("FOUND X IN QUERY: " + userList.get(0).getString("email"));
                    if(userList.size() > -1){
                        receiver = userList.get(0).getString("email");
                        System.out.println(userList);
                        userFound = true;
                    }else{
                        receiver = "";
                        userFound = false;
                    }
                } else {
                    //Log.d("score", "Error: " + e.getMessage());
                    //userModel.setUsername("");
                    System.out.println("ERROR ERROR");
                }
            }
        });
    }
    
    public boolean checkReceiverSlow(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("email", receiver);
        System.out.println("RUNNING QUERY WITH THIS: " + receiver);
        
        List<ParseObject> list = new ArrayList<ParseObject>();
        
        query.limit(1);
        try {
            System.out.println("RUNNING QUERY NOW");
            list = query.find();
        } catch (ParseException ex) {
            Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(list);
        
        if(!list.isEmpty()){
            System.out.println("RETURNING TRUE");
            return true;
        }else{
            System.out.println("RETURNING FALSE");
            return false;
        }
        
    }
    
}

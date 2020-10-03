package dtos;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NotiDTO {
    private String actor, msg;
    private int postId;
    private Timestamp dateTime;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public NotiDTO(String actor, int postId, String msg, Timestamp dateTime) {
        this.actor = actor;
        this.msg = msg;
        this.postId = postId;
        this.dateTime = dateTime;
    }


    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getDatTimeString(){
        return dateFormat.format(dateTime);
    }
}

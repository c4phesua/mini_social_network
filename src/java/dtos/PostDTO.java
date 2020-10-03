package dtos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;

public class PostDTO {
    private BigDecimal id;
    private String email;
    private String title;
    private String desc;
    private byte[] img;
    private Timestamp dateTime;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private ArrayList<CommentDTO> comments;
    private HashSet<String> likes;
    private HashSet<String> dislikes;

    public PostDTO(BigDecimal id, String email, String title, String desc, byte[] img, Timestamp dateTime) {
        this.id = id;
        this.email = email;
        this.title = title;
        this.desc = desc;
        this.img = img;
        this.dateTime = dateTime;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTimeString(){
        return dateFormat.format(dateTime);
    }

    public String getImgBase64(){
        return new String(Base64.getEncoder().encode(img));
    }

    public int getDisLikesNum() {
        return dislikes.size();
    }

    public void setDisLikes(HashSet<String> dislikes) {
        this.dislikes = dislikes;
    }

    public int getLikesNum() {
        return likes.size();
    }

    public void setLikes(HashSet<String> likes) {
        this.likes = likes;
    }

    public ArrayList<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentDTO> comments) {
        this.comments = comments;
    }

    public boolean isLike(String email){
        return likes.contains(email);
    }

    public boolean isDislike(String email){
        return dislikes.contains(email);
    }
}

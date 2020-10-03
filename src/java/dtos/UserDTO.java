package dtos;

import java.util.ArrayList;

public class UserDTO {
    private String email, name, status;
    private ArrayList<NotiDTO> notifications = new ArrayList<>();
    private ArrayList<PostDTO> posts = new ArrayList<>();

    public UserDTO(String email, String name, String status){
        this.email = email;
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<NotiDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<NotiDTO> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<PostDTO> posts) {
        this.posts = posts;
    }
}

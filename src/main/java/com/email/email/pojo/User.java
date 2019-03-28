package com.email.email.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "`user`")
public class User implements Serializable {private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`username`")
    private String username;

    @Column(name = "`password`")
    private String password;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`postbox`")
    private String postbox;

    @Column(name = "`post_password`")
    private String postPassword;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", postbox='" + postbox + '\'' +
                ", postPassword='" + postPassword + '\'' +
                '}';
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return postbox
     */
    public String getPostbox() {
        return postbox;
    }

    /**
     * @param postbox
     */
    public void setPostbox(String postbox) {
        this.postbox = postbox;
    }

    /**
     * @return post_password
     */
    public String getPostPassword() {
        return postPassword;
    }

    /**
     * @param postPassword
     */
    public void setPostPassword(String postPassword) {
        this.postPassword = postPassword;
    }
}
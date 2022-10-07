package com.naverapi.naverapi.article.domain.email;

import javax.persistence.*;

import com.naverapi.naverapi.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 256, nullable = false)
    private String address;

    @Column(length = 256, nullable = false)
    private String title;

    @Column(length = 4096, nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "GL_USER_ID")
    private User user;

    public void setUser(User user) {
        if(this.user != null ){
            this.user.getEmailList().remove(this);
        }
        this.user = user;
        user.getEmailList().add(this);
    }

    @Builder
    public Email(String address, String title, String message){
        this.address = address;
        this.title = title;
        this.message = message;
    }
}

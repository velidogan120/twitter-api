package com.twitter.twitterapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tweet")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tweet")
    private Set<Like> likes;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tweet")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tweet")
    private List<Retweet> retweets;

}

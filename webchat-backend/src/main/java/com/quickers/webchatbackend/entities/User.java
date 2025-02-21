package com.quickers.webchatbackend.entities;

import com.quickers.webchatbackend.common.BaseAuditingEntity;
import com.quickers.webchatbackend.common.UserConstants;
import com.quickers.webchatbackend.entities.Chat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name = UserConstants.FIND_USER_BY_EMAIL,
query = "SELECT u FROM User u WHERE u.email = :email")
@NamedQuery(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF,
        query = "SELECT u FROM User u WHERE u.id != :publicId")
@NamedQuery(name = UserConstants.FIND_USER_BY_PUBLIC_ID,
        query = "SELECT u FROM User u WHERE u.id = :publicId")

public class User extends BaseAuditingEntity {

    private static final int LAST_ACTIVE_INTERNAL = 5;
    @Id
    private String id;
    private String fistName;
    private String lastName;
    private String email;
    private LocalDateTime lastSeen;

    @OneToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender;
    @OneToMany(mappedBy = "recipient")
    private List<Chat> chatsAsRecipient;

    @Transient
    public boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter(
                LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERNAL)
        );
    }
}

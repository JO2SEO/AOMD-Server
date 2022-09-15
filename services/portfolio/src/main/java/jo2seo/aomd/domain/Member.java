package jo2seo.aomd.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Email @NotNull
    private String email;
    @NotNull
    private String password;
    private String profileImgUrl;
    @NotNull
    private String nickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Member(String email, String password, String profileImgUrl, String nickname, UserRole role) {
        this.email = email;
        this.password = password;
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
        this.role = role;
    }

    @CreatedDate
    private LocalDateTime createdAt;

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, profileImgUrl, nickname);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member that = (Member) obj;
        return id.equals(that.getId()) &&
                email.equals(that.getEmail()) &&
                password.equals(that.getPassword()) &&
                profileImgUrl.equals(that.getProfileImgUrl()) &&
                nickname.equals(that.getNickname());
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }
}

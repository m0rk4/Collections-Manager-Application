package by.mark.mangareviewer.domain.user;

import by.mark.mangareviewer.domain.Item;
import by.mark.mangareviewer.domain.Views;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
@Data
@EqualsAndHashCode(of = {"id"})
public class User implements UserDetails, Serializable {
    @Id
    @JsonView(Views.Id.class)
    private String id;
    @JsonView(Views.IdName.class)
    private String name;
    @JsonView(Views.IdName.class)
    private String email;
    @JsonView(Views.IdName.class)
    private boolean nonLocked;
    @JsonView(Views.IdName.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisit;
    @JsonView(Views.IdName.class)
    private String userpic;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @JsonView(Views.IdName.class)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "usr_role",
            joinColumns = @JoinColumn(name = "usr_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private String password;

    @OneToMany(mappedBy = "user")
    private Set<by.mark.mangareviewer.domain.Collection> collections = new HashSet<>();

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
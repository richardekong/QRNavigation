package com.team1.qrnavigationproject.model;


import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.*;

import static com.team1.qrnavigationproject.model.Constant.PASSWORD_REGEX;
import static com.team1.qrnavigationproject.model.Constant.USERNAME_REGEX;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
@Entity
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Username must be provided")
    @Pattern(regexp = USERNAME_REGEX,
            message = "Invalid username")
    private String username;

    @NotBlank(message = "Password must be provided")
    @Pattern(regexp = PASSWORD_REGEX,
            message = "Invalid password, " +
                    "password must contain 7 characters at least," +
                    "with at least one special character," +
                    " one digit, one lower and upper case ")
    private String password;

    @Min(value = 5, message = "User must not be less than 5 years")
    @Max(value = 120)
    private int age;

    @Column("org_id")
    private int organizationId;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserType> userTypes;

    @Column("type_id")
    private int typeId;

    @Column("is_account_expired")
    private boolean isAccountExpired;

    @Column("is_credential_expired")
    private boolean isCredentialExpired;

    @Column("is_credential_locked")
    private boolean isAccountLocked;

    @Column("is_account_enabled")
    private boolean isAccountEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    public void add(Role role) {
        if (roles == null){
            roles = new LinkedList<>();
        }
        if (!roles.contains(role)){
            roles.add(role);
            role.setUser(this);
        }
    }

    public void add(UserType userType){
        if (userTypes==null){
            userTypes = new LinkedList<>();
        }
        if(!userTypes.contains(userType)){
            userTypes.add(userType);
            userType.setUser(this);
        }
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return isAccountEnabled;
    }
}

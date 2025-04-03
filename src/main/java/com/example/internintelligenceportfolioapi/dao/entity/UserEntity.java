package com.example.internintelligenceportfolioapi.dao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> skills;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonManagedReference
    private List<EducationEntity> educationEntities;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonManagedReference
    private List<ExperienceEntity> experienceEntities;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonManagedReference
    private List<ProjectEntity> projectEntities;

    public UserEntity(){
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        if (email != null) {
            return email;
        }
        return null;
    }

    @Override
    public String getPassword() {
        if (password != null) {
            return password;
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

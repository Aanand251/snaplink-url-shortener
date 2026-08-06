package com.anand.url_shortner.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UrlMapping> urls = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.USER;

    // ---------------- Moderation ----------------

    @Column(nullable = false)
    @Builder.Default
    private boolean marked = false;

    @Enumerated(EnumType.STRING)
    private MarkReason markedReason;

    private LocalDateTime markedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marked_by")
    private User markedBy;

    @Column(nullable = false)
    @Builder.Default
    private boolean suspended = false;

    @Enumerated(EnumType.STRING)
    private SuspensionType suspensionType;

    private LocalDateTime suspendedUntil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suspended_by")
    private User suspendedBy;

    private LocalDateTime suspendedAt;

    // --------------------------------------------

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + role.name())
        );
    }

    @Override
    @NonNull
    public String getUsername() {
        return email;
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
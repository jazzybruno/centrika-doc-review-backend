package rw.ac.rca.centrika.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rw.ac.rca.centrika.enumerations.EGender;
import rw.ac.rca.centrika.enumerations.EStatus;

import java.util.Date;
import java.util.Set;
import java.util.UUID;


@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}), @UniqueConstraint(columnNames = {"phone_number"})})
@OnDelete(action = OnDeleteAction.CASCADE)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column (name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    private EGender gender;
    @JsonIgnore
    @Column(name = "password")
    @NotBlank (message = "Password is required")
    private String password;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EStatus status;
    @Column(name = "verified", columnDefinition = "boolean default false")
    private boolean verified = false;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "activation_code")
    private String activationCode;
    // Define the many-to-one relationship with department
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Define the many-to-many relationship with roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User(String username, String phoneNumber, String email, EGender gender, String password, EStatus status, boolean verified , String activationCode) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.status = status;
        this.verified = verified;
        this.activationCode =  activationCode;
    }
}

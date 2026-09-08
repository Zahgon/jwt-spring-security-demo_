package org.zerhusen.security.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "USER")
public class User implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonIgnore
   @Id
   @Column(name = "ID")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
   @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
   private Long id;

   @NotNull
   @Size(min = 4, max = 50)
   @Column(name = "USERNAME", length = 50, unique = true)
   private String username;

   @JsonIgnore
   @NotNull
   @Size(min = 4, max = 100)
   @Column(name = "PASSWORD", length = 100)
   private String password;

   @NotNull
   @Size(min = 4, max = 50)
   @Column(name = "FIRSTNAME", length = 50)
   private String firstname;

   @NotNull
   @Size(min = 4, max = 50)
   @Column(name = "LASTNAME", length = 50)
   private String lastname;

   @NotNull
   @Size(min = 4, max = 50)
   @Column(name = "EMAIL", length = 50)
   private String email;

   @JsonIgnore
   @NotNull
   @Column(name = "ACTIVATED")
   private boolean activated;

   @ManyToMany
   @JoinTable(
      name = "USER_AUTHORITY",
      joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") },
      inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "NAME") })
   @BatchSize(size = 20)
   private Set<Authority> authorities = new HashSet<>();

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getFirstname() {
      return firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      User user = (User) o;
      return Objects.equals(id, user.id);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }

   @Override
   public String toString() {
      return "User{" +
         "username='" + username + '\'' +
         ", password='" + password + '\'' +
         ", firstname='" + firstname + '\'' +
         ", lastname='" + lastname + '\'' +
         ", email='" + email + '\'' +
         ", activated=" + activated +
         '}';
   }
}

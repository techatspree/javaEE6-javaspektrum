package com.acme.youTask.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Entity
@NamedQuery(name = User.QUERY_LOAD_BY_USERNAME, query = "FROM User u WHERE u.login = :" + User.PARAM_USERNAME)
public class User extends AbstractEntity {

  private static final long serialVersionUID = 1L;
  
  // query: load tasks by category
  public static final String QUERY_LOAD_BY_USERNAME = "loadByUsername";
  public static final String PARAM_USERNAME = "login";

  @NotNull
  @Column(unique = true)
  private String login;

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

  @NotNull
  @Pattern(regexp = ".+@.+\\.[a-z]+")
  @Column(unique = true)
  private String email;

  // --------------- constrctor(s) ------------------------------------------------------

  public User() {
    // NOP
  }

  public User(String login, String firstName, String lastName, String email) {
    this.login = login;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  // ---------------- Getter / Setter ---------------------------------------------------

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}

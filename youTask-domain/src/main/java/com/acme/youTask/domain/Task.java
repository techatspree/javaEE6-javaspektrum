package com.acme.youTask.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import com.acme.youTask.domain.enums.Category;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Entity
@NamedQueries({ @NamedQuery(name = Task.QUERY_LOAD_FOR_CATEGORY, query = "FROM Task t WHERE t.category = :" + Task.PARAM_CATEGORY),
  @NamedQuery(name = Task.QUERY_LOAD_FOR_USER, query = "FROM Task t WHERE t.user.login = :" + Task.PARAM_USERNAME) })
public class Task extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  public static final String USER_COLUMN = "OWNING_USER";

  // query: load tasks by category
  public static final String QUERY_LOAD_FOR_CATEGORY = "loadForCategory";
  public static final String PARAM_CATEGORY = "category";

  // query: load tasks by username
  public static final String QUERY_LOAD_FOR_USER = "loadForUser";
  public static final String PARAM_USERNAME = "username";

  @NotNull
  @Column
  private String label;

  @NotNull
  @Column
  @Enumerated(EnumType.STRING)
  private Category category;

  @Column
  private boolean finished;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = USER_COLUMN)
  private User user;

  // --------------- constrctor(s) ------------------------------------------------------

  public Task() {
    // NOP
  }

  public Task(final String label, final Category category, final boolean finished, final User user) {
    this.label = label;
    this.category = category;
    this.finished = finished;
    this.user = user;
  }

  // ---------------- Getter / Setter ---------------------------------------------------

  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(final Category category) {
    this.category = category;
  }

  public boolean isFinished() {
    return finished;
  }

  public void setFinished(final boolean finished) {
    this.finished = finished;
  }

  public User getUser() {
    return user;
  }

  public void setUser(final User user) {
    this.user = user;
  }

}

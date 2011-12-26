package com.acme.youTask.domain.enums;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
public enum Category implements I18nEnum {

  TODO("com.acme.youTask.category.todo"), // (RED),
  WAITING("com.acme.youTask.category.waiting"), // (BLUE)
  IDEA("com.acme.youTask.category.idea"), // (GREEN)
  READ("com.acme.youTask.category.read"), // (YELLOW)
  PRIVATE("com.acme.youTask.category.private"); // (PINK)

  private final String i18nDescriptionId;

  private Category(final String i18nDescriptionId) {
    this.i18nDescriptionId = i18nDescriptionId;
  }

  @Override
  public String getI18nDescriptionId() {
    return i18nDescriptionId;
  }

}

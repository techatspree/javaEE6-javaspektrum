package com.acme.youTask.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
  
    private static final long serialVersionUID = 1L;

    public static final String VERSION_COLUMN = "LAST_SAVED";
    public static final String ID_COLUMN = "ID";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN)
    private Long id;

    @Version
    @Column(name = VERSION_COLUMN)
    private Date version;

    // -------------- Konstruktoren -------------------------------------------

    public AbstractEntity() {
        // NOP
    }

    public Long getId() {
        return id;
    }

    public Date getVersion() {
        return version;
    }

    /**
     * Never override these methods when using JPA/Hibernate
     */
    @Override
    public final boolean equals(final Object obj) {
        return super.equals(obj);
    }

    /**
     * Never override these methods when using JPA/Hibernate
     */
    @Override
    public final int hashCode() {
        return super.hashCode();
    }

}

package com.spring_db.exceptions;

import org.hibernate.HibernateException;

public class DaoExceptions extends HibernateException {
    public DaoExceptions(String message) {
        super(message);
    }

    public DaoExceptions(Throwable cause) {
        super(cause);
    }

    public DaoExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}

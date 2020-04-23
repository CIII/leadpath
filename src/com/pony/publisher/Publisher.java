package com.pony.publisher;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/2/11
 * Time: 11:13 PM
 */
public class Publisher {
    private final Long id;
    private final String name;
    private final boolean isTest;
    private final Boolean allowDuplicates;
    private final Boolean extendedValidation;
    private final String domain;
    private final String username;
    private final String password;
    private final String domainToken;

    private Publisher(Long id, String name, boolean allowDuplicates, boolean useExtendedValidation) {
    	this(id, name, allowDuplicates, useExtendedValidation, null, null, null, null);
    }
    
    public Publisher(Long id, String name, Boolean allowDuplicates, Boolean useExtendedValidation, String domain, String username, String password, String domainToken) {
        this.id = id;
        this.name = name;
        this.isTest = (name.equals("test"));
        this.allowDuplicates = allowDuplicates;
        this.extendedValidation = useExtendedValidation;
        this.domain = domain;
        this.username = username;
        this.password = password;
        this.domainToken = domainToken;
    }

    public static Publisher create(ResultSet rs) throws SQLException {
        return create(rs.getLong("id"), rs.getString("name"), rs.getBoolean("allow_duplicates"), rs.getBoolean("extended_validation"));
    }

    public static Publisher create(Long id, String name, boolean allowDuplicates, boolean useExtendedValidation) throws SQLException {
        return new Publisher(id, name, allowDuplicates, useExtendedValidation);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isTest() {
        return isTest;
    }

    public Boolean allowDuplicates() {
        return allowDuplicates;
    }

    public String getDomain() {
		return domain;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getDomainToken() {
		return domainToken;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Publisher publisher = (Publisher) o;

        if (!name.equals(publisher.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Boolean isExtendedValidation() {
        return extendedValidation;
    }
}

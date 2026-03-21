package com.trier.trier_report.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
@Table(name = "accounts", schema = "tr")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "account_type_id")
    private long accountTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "currency_id")
    private long currencyId;

    @Column(name = "archived")
    private boolean archived;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected Account() {
    }

    public Account(long accountTypeId, long currencyId, String name) {
        this.accountTypeId = accountTypeId;
        this.name = name;
        this.currencyId = currencyId;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", userId=" + userId + ", accountTypeId=" + accountTypeId + ", name='" + name + '\'' + ", currencyId='" + currencyId + '\'' + ", archived=" + archived + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}

package com.shanshui.smartcommunity.user.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.IndexColumn

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OrderColumn

/**
 * Created by I336253 on 11/19/2017.
 */
@Entity
class User implements Serializable {
    @Id
    @GeneratedValue
    Long id

    String userName
    String firstName
    String lastName
    String gender
    Date createdDate
    Date lastAccessed
    String deviceId
    @Column(unique = true, nullable = false)
    String cellPhoneNumber
    String userAgent
    @OneToMany(cascade = CascadeType.ALL, targetEntity = PaymentAccount.class)
    @OrderColumn(name = 'id')
    List<PaymentAccount> paymentAccounts
    String photo
    String token
}

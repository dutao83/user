package com.shanshui.smartcommunity.user.domain

import javax.persistence.*

/**
 * Created by I336253 on 11/19/2017.
 */
@Entity
class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @OneToMany(cascade = CascadeType.ALL, targetEntity = PaymentAccount.class, fetch = FetchType.EAGER)
    @OrderColumn(name = 'id')
    List<PaymentAccount> paymentAccounts
    String photo
    String token
}

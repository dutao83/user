package com.shanshui.smartcommunity.user.domain

import javax.persistence.*

/**
 * Created by I336253 on 11/21/2017.
 */
@Entity
class PaymentAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    @Enumerated(EnumType.STRING)
    PaymentChannel channel
    String accountNumber
    @ManyToOne(fetch = FetchType.EAGER)
    User owner

    @Enumerated(EnumType.STRING)
    AccountType type
    enum AccountType {
        ENTERPRISE,
        PERSONAL
    }
}

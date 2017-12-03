package com.shanshui.smartcommunity.user.domain

import org.springframework.data.annotation.Transient
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id


/**
 * Created by I336253 on 11/21/2017.
 */
@Entity
class PaymentAccount implements Serializable {
    @Id
    @GeneratedValue
    Long id

    @Enumerated(EnumType.STRING)
    PaymentChannel type
    String accountNumber

    @Transient
    User owner
}

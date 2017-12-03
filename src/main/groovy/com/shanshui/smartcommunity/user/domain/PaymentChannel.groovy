package com.shanshui.smartcommunity.user.domain

import javax.persistence.Entity
import javax.persistence.Id

/**
 * Created by I336253 on 11/21/2017.
 */
enum PaymentChannel {
    WECHATPAY('WECHATPAY', 'WeChat pay'),
    ALIPAY('ALIPAY', 'Ali pay');

    String name
    String description

    protected PaymentChannel(String name, String description) {
        this.name = name
        this.description = description
    }
}

package com.shanshui.smartcommunity.user.service

import com.shanshui.smartcommunity.user.domain.User
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Service

/**
 * Created by I336253 on 12/3/2017.
 */
@Service
@EnableCaching
class TokenService {
    /**
     * generate unique token for a specified user
     * @param cellPhone
     * @param createDate
     * @param userAgent
     * @param deviceId
     * @return
     */
    def generateToken(final User user) {
        if (user == null) {
            throw new IllegalArgumentException('please provide user information to proceed')
        }
        if (user.cellPhoneNumber == null || user.deviceId == null) {
            throw new IllegalArgumentException('cell phone number or device id could not be null')
        }
        String footprint = "${user.createdDate.toString()}${user.cellPhoneNumber}${user.deviceId}${user.userAgent}"
        DigestUtils.sha1Hex(footprint)
    }
}

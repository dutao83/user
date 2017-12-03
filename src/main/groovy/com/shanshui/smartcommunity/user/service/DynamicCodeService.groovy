package com.shanshui.smartcommunity.user.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Service

/**
 * Created by I336253 on 12/3/2017.
 */
@Service
@EnableCaching
class DynamicCodeService {
    @Cacheable(value = 'dynamicCode', key = '#p0')
    def generateDynamicCode(String cellPhone) {
        LOGGER.info("generating dynamic code for $cellPhone")
        def code = new Random().nextInt(9000) + 1000
        LOGGER.info("generated code: $code")
        return code
    }

    def sendDynamicCode(String cellPhone, int code) {
        LOGGER.info("sending dynamic code to $cellPhone")
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicCodeService.class)
}

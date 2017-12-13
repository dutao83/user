package com.shanshui.smartcommunity.user.controller

import com.shanshui.smartcommunity.user.domain.User
import com.shanshui.smartcommunity.user.domain.UserRepository
import com.shanshui.smartcommunity.user.service.DynamicCodeService
import com.shanshui.smartcommunity.user.service.TokenService
import com.shanshui.smartcommunity.user.service.exception.UserException
import io.swagger.annotations.Api
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletResponse

/**
 * Created by I336253 on 11/30/2017.
 */
@Service(value = 'Registration')
@RequestMapping("/register")
@EnableCaching
@Api(value = "REGISTER API", description = "REST API for user registration", tags = ['Register API'])
class Registration {
    @Autowired
    UserRepository repository
    @Autowired
    DynamicCodeService dynamicCodeService
    @Autowired
    TokenService tokenService
    /**
     * tell server to send the dynamic code to the cell phone number
     * the code will be temporarily stored in a cache, with a timeout mechanism
     * @param cellPhone
     */
    @RequestMapping(value = '/{cellphone}', method = RequestMethod.GET)
    @ResponseBody
    def fetchDynamicCode(@PathVariable('cellphone') String cellPhone) {
        def user = repository.findByCellPhoneNumber(cellPhone)
        if (user) {
            return UserException.USER_REGISTERED
        }
        def code = dynamicCodeService.generateDynamicCode(cellPhone)
        // send the dynamic code here
        dynamicCodeService.sendDynamicCode(cellPhone, code)
        // temp for test TODO: comment out
        code
    }

    /**
     * check if the code is correct by comparing the input with the cached value at server side
     * @param cellPhone
     * @param code
     * @param user some basic user information should be sent along with the request
     */
    @RequestMapping(value = '/{cellphone}/{code}', method = RequestMethod.POST)
    @ResponseBody
    def register(
            @PathVariable('cellphone') String cellPhone,
            @PathVariable('code') int code, final @RequestBody User user) {
        LOGGER.info("register for user: $cellPhone")
        def benchMark = dynamicCodeService.generateDynamicCode(cellPhone)
        if (code == benchMark) {
            user.cellPhoneNumber = cellPhone
            user.createdDate = Calendar.instance.time

            user.token = tokenService.generateToken(user)

            User usr = repository.save(user)
            registered(cellPhone, user)
            return usr
            // return token here
        } else {
            // how to deliver error information
            throw UserException.WRONG_DYNAMIC_CODE
        }
    }

    @CacheEvict(value = 'dynamicCode', key = '#cellPhone')
    def registered(String cellPhone, User user) {
        LOGGER.info("$cellPhone registered successfully")
    }
    private final static Logger LOGGER = LoggerFactory.getLogger(Registration.class)
}

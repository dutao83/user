package com.shanshui.smartcommunity.user.controller

import com.shanshui.smartcommunity.user.domain.User
import com.shanshui.smartcommunity.user.domain.UserRepository
import com.shanshui.smartcommunity.user.service.exception.UserException
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.web.bind.annotation.*

/**
 * Created by I336253 on 11/19/2017.
 */
@RestController
@RequestMapping('/user')
@EnableAutoConfiguration
@EnableCaching
@Api(value = "USER API", description = "REST API for user", tags = ['User API'])
class UserController {

    @Autowired
    UserRepository repository

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    def get(@RequestHeader(value = "token") String token) {
        repository.findAll()
    }

    /**
     * internal used for services
     * @param id
     * @param token
     * @return
     */
    @RequestMapping(value = '/{id}', method = [RequestMethod.GET])
    //@Cacheable(value = 'user', key = "'#id'")
    @ResponseBody
    def get(@PathVariable('id') long id) {
        id ? repository.findOne(id) : null
    }

    /**
     * internal used for services
     * @param id
     * @return
     */
    @RequestMapping(value = '/{id}/token', method = RequestMethod.GET)
    @Cacheable(value = 'token', key = "'#id'")
    // cache
    @ResponseBody
    def getToken(@PathVariable('id') long id) {
        get(id)?.token // cache the token for further request
    }

    /**
     * update user information
     * @param id
     * @param token
     * @param user
     * @return
     */
    @RequestMapping(value = '/{id}', method = RequestMethod.POST)
    @CacheEvict(value = 'token', key = '#id')
    // remove cache in case any change happens
    @ResponseBody
    def put(@PathVariable('id') long id, @RequestHeader(value = "token") String token, @RequestBody User user) {
        // only user himself could update the information
        if (getToken(id) != token) {
            return UserException.WRONG_TOKEN
        }
        id == user.id ? repository.save(user) : null
    }

    @RequestMapping(value = '/{id}/payment/account', method = RequestMethod.GET)
    @ResponseBody
    def getPaymentAccounts(@PathVariable('id') Long id, @RequestHeader(value = "token") String token) {
        // only user himself could view the payment information
        if (getToken(id) != token) {
            return UserException.WRONG_TOKEN
        }
        get(id)?.paymentAccounts
    }

    @RequestMapping(value = '/{id}/payment/account/{pid}', method = RequestMethod.GET)
    @ResponseBody
    def getPaymentAccount(
            @PathVariable('id') long id, @PathVariable('pid') Long pid, @RequestHeader(value = "token") String token) {
        // only user himself could view the payment information
        if (getToken(id) != token) {
            return UserException.WRONG_TOKEN
        }
        get(id)?.paymentAccounts?.find { account ->
            account.id == pid
        }
    }

    public static void main(String[] args) {
        SpringApplication.run UserController.class, args
    }
}

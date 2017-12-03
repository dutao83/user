package com.shanshui.smartcommunity.user.controller

import com.shanshui.smartcommunity.user.domain.PaymentAccountRepository
import com.shanshui.smartcommunity.user.domain.User
import com.shanshui.smartcommunity.user.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
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
class UserController {

    @Autowired
    UserRepository repository

    @RequestMapping(method = RequestMethod.GET)
    def get() {
        repository.findAll()
    }

    @RequestMapping(value = '/{id}', method = [RequestMethod.GET])
    def get(@PathVariable('id') Long id) {
        id ? repository.findOne(id) : null
    }

    @RequestMapping(value = '/{id}/token', method = RequestMethod.GET)
    @Cacheable(value = 'token', key = '#id')
    def getToken(@PathVariable('id') Long id) {
        get(id)?.token // cache the token for further request
    }

    @RequestMapping(value = '/{id}', method = RequestMethod.PUT)
    def put(@PathVariable('id') Long id, @RequestBody User user) {
        id == user.id ? repository.save(user) : null
    }

    @RequestMapping(value = '/{id}/payment/account', method = RequestMethod.GET)
    def getPaymentAccounts(@PathVariable('id') Long id) {
        get(id)?.paymentAccounts
    }

    @RequestMapping(value = '/{id}/payment/account/{pid}', method = RequestMethod.GET)
    def getPaymentAccount(@PathVariable('id') Long id, @PathVariable('pid') Long pid) {
        get(id)?.paymentAccounts?.find { account ->
            account.id == pid
        }
    }

    public static void main(String[] args) {
        SpringApplication.run UserController.class, args
    }
}

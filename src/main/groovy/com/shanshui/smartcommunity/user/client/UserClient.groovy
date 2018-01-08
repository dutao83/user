package com.shanshui.smartcommunity.user.client

import org.springframework.cache.annotation.Cacheable
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.*

/**
 * Created by I336253 on 12/27/2017.
 */
@FeignClient("user")
public interface UserClient {
    /**
     * internal used for services
     * @param id
     * @param token
     * @return
     */
    @RequestMapping(value = '/{id}', method = [RequestMethod.GET])
    //@Cacheable(value = 'user', key = "'#id'")
    @ResponseBody
    def get(@PathVariable('id') long id)

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    def get(@RequestBody List<Long> ids)

    /**
     * internal used for services
     * @param id
     * @return
     */
    @RequestMapping(value = '/{id}/token', method = RequestMethod.GET)
    @Cacheable(value = 'token', key = "'#id'")
    // cache
    @ResponseBody
    def getToken(@PathVariable('id') long id)

    @RequestMapping(value = '/{id}/payment/account', method = RequestMethod.GET)
    @ResponseBody
    def getPaymentAccounts(@PathVariable('id') Long id, @RequestHeader(value = "token") String token)

    @RequestMapping(value = '/{id}/payment/account/{pid}', method = RequestMethod.GET)
    @ResponseBody
    def getPaymentAccount(
            @PathVariable('id') long id, @PathVariable('pid') Long pid, @RequestHeader(value = "token") String token)
}
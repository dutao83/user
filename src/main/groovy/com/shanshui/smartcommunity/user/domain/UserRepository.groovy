package com.shanshui.smartcommunity.user.domain

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * Created by I336253 on 11/19/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    @Query('select u from User u where u.cellPhoneNumber = ?1')
    User findByCellPhoneNumber(String cellPhoneNumber)

    @Query('select u from User u where u.token = :token')
    User findByToken(String token)
}
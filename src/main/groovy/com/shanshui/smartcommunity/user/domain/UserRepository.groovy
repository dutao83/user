package com.shanshui.smartcommunity.user.domain

import org.springframework.data.repository.CrudRepository

/**
 * Created by I336253 on 11/19/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByCellPhoneNumber(String cellPhoneNumber)

    User findByToken(String token)
}
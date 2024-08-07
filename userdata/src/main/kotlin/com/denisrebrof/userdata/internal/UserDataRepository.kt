package com.denisrebrof.userdata.internal

import com.denisrebrof.userdata.model.UserData
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
interface UserDataRepository : JpaRepository<UserData, Long> {
    fun findUserDataByUsername(username: String): List<UserData>
    fun findUserDataById(userId: Long): UserData?
    fun findUserDataByLocalId(userId: String): UserData?
    fun findUserDataByYandexId(yandexId: String): UserData?

    @Modifying
    @Query("update UserData user set user.primaryWeapon = :weaponId where user.id = :userId")
    fun setPrimaryWeapon(
        @Param("userId") userId: Long?,
        @Param("weaponId") weaponId: Long
    ): Int

    @Modifying
    @Query("update UserData user set user.secondaryWeapon = :weaponId where user.id = :userId")
    fun setSecondaryWeapon(
        @Param("userId") userId: Long?,
        @Param("weaponId") weaponId: Long
    ): Int

    @Query(
        "SELECT COUNT(*) FROM UserData AS u "
            + "WHERE u.rating > :userRating OR (u.rating = :userRating AND u.id > :userId) "
    )
    fun getUserRatingPos(
        @Param("userId") userId: Long,
        @Param("userRating") userRating: Int
    ): Long

    @Query(
        "SELECT u FROM UserData AS u "
            + "WHERE u.rating > :userRating OR (u.rating = :userRating AND u.id > :userId) "
            + "ORDER BY u.rating, u.id"
    )
    fun getUsersWithRatingMore(
        @Param("userId") userId: Long,
        @Param("userRating") userRating: Int,
        pageable: Pageable
    ): List<UserData>

    @Query(
        "SELECT u FROM UserData AS u "
            + "WHERE u.rating < :userRating OR (u.rating = :userRating AND u.id < :userId) "
            + "ORDER BY u.rating, u.id"
    )
    fun getUsersWithRatingLess(
        @Param("userId") userId: Long,
        @Param("userRating") userRating: Int,
        pageable: Pageable
    ): List<UserData>
}
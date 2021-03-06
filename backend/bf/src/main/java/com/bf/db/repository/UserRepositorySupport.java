package com.bf.db.repository;

import com.bf.db.entity.QUser;
import com.bf.db.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;

    public Optional<User> findUserByUserId(String userId) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.userId.eq(userId)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    public Optional<User> findUserByUserEmail(String userEmail) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.userEmail.eq(userEmail)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    public Optional<User> findUserByUserNameAndUserEmail(String userName, String userEmail) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where((qUser.userEmail.eq(userEmail)).and(qUser.userName.eq(userName)))
                .fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }


}

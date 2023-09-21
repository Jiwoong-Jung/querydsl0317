package com.study.springboot.repositroy;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.springboot.dto.TableDto;
import com.study.springboot.entity.Emp;
import com.study.springboot.entity.QEmp;
import com.study.springboot.entity.QTables;
import com.study.springboot.entity.Tables;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TablesRepositoryTest {

    @Autowired
    TablesRepository tablesRepository;
    private JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void test1() {
        QTables tt = QTables.tables;
        Tables tables = entityManager.find(Tables.class, 1L);
        System.out.println(tables);

        jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Long> count = jpaQueryFactory.select(Wildcard.count).from(QTables.tables);
        System.out.println(count.stream().count());
        QTables user = QTables.tables;
        List<Tables> names = jpaQueryFactory.select(user)
                .from(user)
                .where(user.tid.between(3, 7))
                .orderBy(user.name.asc())
                .fetch();
        System.out.println(names);

        QTables user1 = QTables.tables;
        String name = "abc4";
        String number = "56";
        BooleanBuilder builder = new BooleanBuilder();
        if (name != null) {
            builder.and(user.name.eq(name));
        }
        if (number != null) {
            builder.and(user.number.eq(number));
        }
        List<Tables> users = jpaQueryFactory.selectFrom(user)
                .where(builder)
                .fetch();
        System.out.println(users);
    }

    @Test
    public void test2() {
        QEmp emp = QEmp.emp;
        jpaQueryFactory = new JPAQueryFactory(entityManager);
        List<Tuple> members = jpaQueryFactory
                .select(emp.empno, emp.ename, emp.sal)
                .from(emp)
                //.where(emp.empno.eq(7654L))
                .fetch();
        System.out.println(members);
    }
}
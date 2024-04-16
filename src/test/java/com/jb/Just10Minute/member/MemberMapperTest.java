package com.jb.Just10Minute.member;

import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    @DisplayName("MemberMapper가 null이 아님")
    public void MemberMapper_is_not_null() {
        assertThat(memberMapper).isNotNull();
    }

    @Mapper
    interface MemberMapper {
        
    }
}

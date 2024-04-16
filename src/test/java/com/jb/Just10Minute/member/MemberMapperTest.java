package com.jb.Just10Minute.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@MybatisTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    @DisplayName("MemberMapper가 null이 아님")
    public void MemberMapper_is_not_null() {
        assertThat(memberMapper).isNotNull();
    }

    @Test
    @DisplayName("mybatis 테스트 : id가 'testId' 인 멤버 꺼내오기")
    public void mybatisTest() {
        final String id = "testId";
        Member member = memberMapper.findById(id);
        assertThat(id).isEqualTo(member.getId());
    }

    @Mapper
    interface MemberMapper {

        @Select("SELECT * FROM member WHERE id = #{id}")
        public Member findById(final String id);
    }

    @Data
    @Builder
    static class Member {
        private String id;
        private String password;
        private String role;
    }
}

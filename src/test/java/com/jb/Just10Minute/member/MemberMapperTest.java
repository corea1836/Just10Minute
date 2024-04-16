package com.jb.Just10Minute.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
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
    @DisplayName("id로 멤버 찾기 테스트")
    public void findById() {
        //given
        final String id = "initId";

        //when
        Member member = memberMapper.findById(id);

        //then
        assertThat(member.getId()).isEqualTo("initId");
        assertThat(member.getPassword()).isEqualTo("initPassword");
        assertThat(member.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    @DisplayName("멤버 등록 테스트")
    public void add_member() {
        //given
        Member member = Member.builder()
                .id("testId")
                .password("testPassword")
                .role(Role.PUBLIC)
                .build();

        //when
        final int saveResult = memberMapper.save(member);
        Member saveMember = memberMapper.findById(member.getId());

        //then
        assertThat(saveResult).isEqualTo(1);
        assertThat(saveMember.getId()).isEqualTo(member.getId());
        assertThat(saveMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(saveMember.getRole()).isEqualTo(member.getRole());
    }



    @Mapper
    interface MemberMapper {

        @Select("SELECT * FROM member WHERE id = #{id}")
        public Member findById(final String id);

        @Insert("INSERT INTO member (id, password, role) VALUES (#{id}, #{password}, #{role})")
        public int save(final Member member);
    }

    @Data
    @Builder
    static class Member {
        private String id;
        private String password;
        private Role role;
    }

    enum Role {
        PUBLIC,
        SELLER,
        ADMIN;
    }
}

package com.jb.Just10Minute.member;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService target;

    @Mock
    private MemberMapperTest.MemberMapper memberMapper;

    //test data
    private final String id = "initId";
    private final String password = "initPassword";
    private final MemberMapperTest.Role role = MemberMapperTest.Role.ADMIN;

    //unhappy case

    @Test
    @DisplayName("멤버 등록 실패 : 이미 존재하는 아이디")
    public void signUp_fail_already_exist_id() {
        //given
        SignUpRequestDto dto = SignUpRequestDto.builder()
                .id("initId")
                .password("initPassword")
                .build();

        doReturn(MemberMapperTest.Member.builder().build()).when(memberMapper).findById(id);

        //when
        final MemberException result = assertThrows(MemberException.class, () -> target.signUp(dto));

        //then
        assertThat(result.getErrorResult()).isEqualTo(MemberErrorResult.DUPLICATED_ID);
    }

    @Getter
    @RequiredArgsConstructor
    enum MemberErrorResult {
        DUPLICATED_ID(HttpStatus.BAD_REQUEST, "Duplicated Id Request"),;

        private final HttpStatus httpStatus;
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    static class MemberException extends RuntimeException {
        private final MemberErrorResult errorResult;
    }



    @Service
    @RequiredArgsConstructor
    static class MemberService {

        private final MemberMapperTest.MemberMapper memberMapper;

        public MemberMapperTest.Member signUp(SignUpRequestDto signUpRequestDto) {

            MemberMapperTest.Member member = memberMapper.findById(signUpRequestDto.getId());
            if( member != null) {
                throw new MemberException(MemberErrorResult.DUPLICATED_ID);
            }
            return null;
        }
    }

    @Data
    @Builder
    static class SignUpRequestDto {
        private String id;
        private String password;
    }


}

package com.jb.Just10Minute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class testRunner implements ApplicationRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Connection con = dataSource.getConnection();
        log.info("DB Connection Pool : {}", dataSource.getClass());
        log.info("url : {}", con.getMetaData().getURL());
        log.info("userName : {}", con.getMetaData().getUserName());

        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT * FROM member");
        maps.stream().forEach(m -> log.info(m.toString()));
    }
}

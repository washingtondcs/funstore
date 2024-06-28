package com.challenge.funstore.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
public class SqlScriptScheduler {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SqlScriptScheduler(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Scheduled(initialDelay = 60000, fixedDelay = Long.MAX_VALUE)
    public void executeSqlScript() {
        Resource resource = new ClassPathResource("insert.sql");
        try {
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), resource);
            System.out.println("insert.sql script executed successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to execute insert.sql script.");
            e.printStackTrace();
        }
    }
}
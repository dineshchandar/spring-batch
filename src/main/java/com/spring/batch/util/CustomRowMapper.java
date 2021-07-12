package com.spring.batch.util;

import com.spring.batch.model.Birthday;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomRowMapper implements RowMapper<Birthday> {
    @Override
    public Birthday mapRow(ResultSet rs, int rowNum) throws SQLException {

        Birthday birthday = new Birthday();

        birthday.setEmailId(rs.getString("email_id"));
        birthday.setName(rs.getString("name"));
        birthday.setBirthday(rs.getTimestamp("birthday"));
        birthday.setWishesStatus(rs.getString("wishes_status"));

        return birthday;
    }
}

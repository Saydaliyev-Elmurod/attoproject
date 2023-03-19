package org.example.repository;

import org.example.db.DataBase;
import org.example.dto.Profile;
import org.example.enums.Role;
import org.example.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.example.util.MD5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository

public class ProfileRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registration(Profile profile) {
        String sql = "insert into profile(name,surname,phone,password) " +
                "values(?,?,?,?)";
        jdbcTemplate.update(sql, profile.getName(), profile.getSurname(), profile.getPhone(), profile.getPassword());
    }

    public Profile getProfileByPhone(String phone) {
        String sql = "select * from profile " +
                "where phone = ?";
        List<Profile> profileList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class), phone);
        if (profileList.isEmpty()) {
            return null;
        }
        return profileList.get(0);
    }

    public Profile login(String phone, String password) {
        Profile profile = getProfileByPhone(phone);
        if (profile != null && profile.getPassword().equals(MD5.getMd5Hash(password))) {
            return profile;
        } else {
            return null;
        }
    }

    public List<Profile> profileList() {
        String sql = "select * from profile ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class));
    }

    public void updateProfileStatus(Profile profile) {
        String sql = "update   profile " +
                "set status = ? " +
                "where phone = ? and password =?";
        jdbcTemplate.update(sql, profile.getStatus().name(), profile.getPhone(), profile.getPassword());
    }
}

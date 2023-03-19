package org.example.repository;

import org.example.db.DataBase;
import org.example.dto.Terminal;
import org.example.enums.TerminalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository

public class TerminalRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Terminal getTerminalByNumber(String number) {
        String sql = "select * from terminal " +
                "where number = ?";
        List<Terminal> terminalList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Terminal.class), number);
        if (terminalList.isEmpty()) return null;
        return terminalList.get(0);
    }

    public void createTerminal(String number, String address) {
        String sql = "insert into terminal (number,address)" +
                " values (?,?)";
        jdbcTemplate.update(sql, number, address);
    }

    public List<Terminal> terminalList() {
        String sql = "select * from terminal ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Terminal.class));
    }

    public void updateTerminalByNumber(Terminal terminal, String newTerminalNum, String address) {
        String sql = "update terminal " +
                "set number = ? , address = ? " +
                "where number = ? and address = ?";
        jdbcTemplate.update(sql, newTerminalNum, address, terminal.getNumber(), terminal.getAddress());
    }

    public void updateTerminalStatus(Terminal terminal) {
        String sql = "update terminal " +
                "set status = ? " +
                " where number = ? and address = ?";
        jdbcTemplate.update(sql, terminal.getStatus().name(), terminal.getNumber(), terminal.getAddress());
    }

    public void deleteTerminal(Terminal terminal) {
        String sql = "delete from  terminal " +
                "where number = ? and address = ?";
        jdbcTemplate.update(sql, terminal.getNumber(), terminal.getAddress());
    }
}

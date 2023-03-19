package org.example.repository;
import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.dto.Terminal;
import org.example.dto.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository

public class TransactionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void transaction(Profile profile, Card card, Terminal terminal) {
        String sql = "insert into transaction (profile_id,card_id,terminal_id)" +
                "values(?,?,?)";
        jdbcTemplate.update(sql, profile.getId(), card.getId(), terminal.getId());
    }

    public List<Transaction> transactionList(Profile profile) {
        String sql = "select * from transaction " +
                "where profile_id = ? " +
                "order by  created_date desc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class), profile.getId());
    }

    public List<Transaction> transactionListAllProfile() {
        String sql = "select * from transaction " +
                "order by  created_date desc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
    }

    public List<Transaction> paymentCurrentDay() {
        String sql = "select * from transaction " +
                "where created_date::date = now()::date " +
                "order by  created_date desc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
    }

    public List<Transaction> paymentDay(LocalDate localDate) {
        String sql = "select * from transaction " +
                "where created_date::date = ? " +
                "order by  created_date desc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class), Date.valueOf(localDate));
    }

    public List<Transaction> intermediatePayment(LocalDate dateFrom, LocalDate dateTo) {
        String sql = "select * from transaction " +
                "where created_date >=  ? " +
                "and created_date <=? " +
                "order by  created_date desc ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class), Date.valueOf(dateFrom), Date.valueOf(dateTo));
    }

    public List<Transaction> transactionByTerminal(Terminal terminal) {
        String sql = "select * from transaction " +
                "where terminal_id =  ? " +
                "order by  created_date desc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class), terminal.getId());
    }

    public List<Transaction> transactionByCard(Card card) {
        String sql = "select * from transaction " +
                "where card_id =  ? " +
                "order by  created_date desc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class), card.getId());
    }

    public void refill(Profile profile, Card card, Double amount) {
        String sql = "insert into transaction (profile_id,card_id,amount,type)" +
                "values(?,?,?,'REFILL')";
        jdbcTemplate.update(sql, profile.getId(), card.getId(), amount);
    }
}

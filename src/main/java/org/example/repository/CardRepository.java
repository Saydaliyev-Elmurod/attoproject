package org.example.repository;

import org.example.db.DataBase;
import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.enums.CardStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CardRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Card getCard(String numCard) {
        String sql = "select * from card " +
                "where number = ? ";
        List<Card> cardList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class),numCard);
        if (cardList.isEmpty()) {
            return null;
        } else {
            return cardList.get(0);
        }
    }

    public void addCardToUser(Profile profile, Card card) {
        String sql = "update card  " +
                "set profile_id = ? , added_date = now()," +
                "status ='ACTIVE' " +
                "where id = ? ";
        jdbcTemplate.update(sql, profile.getId(), card.getId());
    }

    public void createCard(String number, String exp_date) {
        String sql = "insert into card (number,exp_date)" +
                "values(?,?)";
        jdbcTemplate.update(sql, number, exp_date);
    }

    public List<Card> cardList() {
        String sql = "select * from card ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class));
    }

    //    public void updateCardByNumber(Card card, String newCardNum, String new_exp_date) {
//        String sql = "update card " +
//                "set number = ? , exp_date = ? " +
//                "where number = ? and exp_date = ?";
//        jdbcTemplate.update(sql,newCardNum,new_exp_date,card.getNumber(),card.getExp_date());
//    }
    public void updateCardByNumber(Card card, String newCardNum, String new_exp_date) {
        String sql = "update card " +
                "set number = :newCardNum , exp_date = :newExpDate " +
                "where number = :oldCardNum and exp_date = :oldCardExpDate";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("newCardNum", newCardNum);
        hashMap.put("newExpDate", new_exp_date);
        hashMap.put("oldCardNum", card.getNumber());
        hashMap.put("oldCardExpDate", card.getExp_date());
        namedParameterJdbcTemplate.update(sql, hashMap);
    }

    public void updateCardStatus(Card card) {
        String sql = "update card " +
                "set status = ? " +
                " where number = ? and exp_date = ?";
        jdbcTemplate.update(sql, card.getStatus().name(), card.getNumber(), card.getExp_date());
    }

    public void deleteCard(Card card) {
        String sql = "delete from card " +
                "where number = ? and exp_date = ?";
        jdbcTemplate.update(sql, card.getNumber(), card.getExp_date());
    }

    public List<Card> cardListUser(Profile profile) {
        String sql = "select * from card " +
                "where profile_id = ? ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class),profile.getId());
    }
    public void updateCardBalance(Card card) {
        String sql = "update card " +
                "set amount = ?" +
                " where number = ? and exp_date = ?";
        jdbcTemplate.update(sql,card.getAmount(),card.getNumber(),card.getExp_date());
    }
}

package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.models.Purchase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class PurchaseRepositoryImpl implements PurchaseRepositorycustom{
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PurchaseRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Purchase> findByUserId(String userId) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return this.mongoTemplate.find(query, Purchase.class);
    }
}
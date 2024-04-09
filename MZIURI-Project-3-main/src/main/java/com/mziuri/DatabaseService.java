package com.mziuri;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class DatabaseService {
    private static DatabaseService instance;
    private EntityManagerFactory emf;

    private DatabaseService() {
        emf = Persistence.createEntityManagerFactory("persistence-unit-name");
    }

    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    public List<Chatroom> getAllChatrooms() {
        EntityManager em = emf.createEntityManager();
        List<Chatroom> chatrooms;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Chatroom> cq = cb.createQuery(Chatroom.class);
            Root<Chatroom> rootEntry = cq.from(Chatroom.class);
            CriteriaQuery<Chatroom> all = cq.select(rootEntry);

            TypedQuery<Chatroom> allQuery = em.createQuery(all);
            chatrooms = allQuery.getResultList();
        } finally {
            em.close();
        }
        return chatrooms;
    }

    public void createChatroom(Chatroom chatroom) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(chatroom);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}

package com.example.chuyen_de_1.service;

import com.example.chuyen_de_1.model.User;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class TempUserService {
    private int userExpiredTime = 30;

    private LoadingCache<Integer, User> userCache;

    public TempUserService() {
        userCache = CacheBuilder.newBuilder()
                .expireAfterWrite(userExpiredTime, TimeUnit.MINUTES)
                .build(new CacheLoader<Integer, User>() {
                    @Override
                    public User load(Integer key) {
                        return null;
                    }
                });
    }

    public int putUser(String username, String password, String email) {
        User user = new User(username, password, email);
        int userId = generateUserId();
        userCache.put(userId, user);
        return userId;
    }

    public User getUser(int userId) throws ExecutionException {
        return userCache.get(userId);
    }

    public void deleteUser(int userId) {
        userCache.invalidate(userId);
    }

    private synchronized int generateUserId() {
        return (int) (userCache.size() + 1);
    }
}
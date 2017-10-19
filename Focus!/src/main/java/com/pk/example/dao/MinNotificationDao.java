package com.pk.example.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.pk.example.MinNotification;
import com.pk.example.entity.MinNotificationEntity;
import com.pk.example.entity.ProfileEntity;

import java.util.List;

@Dao
public interface MinNotificationDao {
    @Query("SELECT * FROM min_notifications")
    LiveData<List<MinNotificationEntity>> loadAllMinNotifications();

    @Delete
    void delete(MinNotificationEntity minNotification);

    @Insert
    void insert(MinNotificationEntity minNotification);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MinNotificationEntity> minNotifications);

    @Query("select * from min_notifications where _id = :minNotificationID")
    LiveData<MinNotificationEntity> loadMinNotification(int minNotificationID);

    @Query("select * from min_notifications where _id = :minNotificationID")
    MinNotificationEntity loadMinNotificationASync(int minNotificationID);

    @Query("select * from min_notifications where _profileName = :profileName")
    List<MinNotificationEntity> loadMinNotificationsFromProfileSync(String profileName);

}
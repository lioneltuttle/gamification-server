package com.game.biz.service;

import com.game.biz.model.Point;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Point}.
 */
public interface NotificationService {

    void sendPushNotification(Long userId, String englishTitle, String frenchTitle, String englishMessage, String frenchMessage);

}

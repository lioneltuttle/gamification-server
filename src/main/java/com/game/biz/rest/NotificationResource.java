package com.game.biz.rest;

import com.game.biz.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * REST controller
 */
@RestController
@RequestMapping("/api")
public class NotificationResource {

    private final Logger log = LoggerFactory.getLogger(NotificationResource.class);


    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationService notificationService;

    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * {@code POST  /notification} : Create a new pushNotification.
     *
     * @param datas the user id.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification")
    public void sendNotification(@RequestBody List<String> datas) {
        log.debug("REST request to send notification : {}", datas.get(0));
        notificationService.sendPushNotification(Long.parseLong(datas.get(0)), datas.get(1),datas.get(2),datas.get(3),datas.get(4));
    }




}

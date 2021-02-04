package com.game.biz.service.impl;

import com.game.biz.model.Point;
import com.game.biz.model.PointsAudit;
import com.game.biz.model.Resultat;
import com.game.biz.model.enumeration.EventType;
import com.game.biz.service.NotificationService;
import com.game.biz.service.PointService;
import com.game.biz.service.PointsAuditService;
import com.game.biz.service.ResultatService;
import com.game.repository.biz.PointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

/**
 * Service Implementation for managing {@link Point}.
 */
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);



    public NotificationServiceImpl() {

    }

    @Override
    public void sendPushNotification(Long userId, String englishTitle, String frenchTitle, String englishMessage, String frenchMessage, boolean isAdmin) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic \u003cNTZkMWY5NGMtOWRiYy00NjRiLTk5ZTYtYmJiNzIzZmU2YThj\u003e");
            con.setRequestMethod("POST");



            String strJsonBody = "{"
                +   "\"app_id\": \"aa9cbc7f-2910-4afe-9cec-ac799f760b8f\","
                +   (isAdmin?"": "\"include_external_user_ids\": [\""+ userId+"\"],")
                +   "\"channel_for_external_user_ids\": \"push\","
                +   "\"data\": {\"foo\": \"bar\"},"
                +   "\"action\": \"OK\","
                + (isAdmin?"\"filters\": [{\"field\": \"tag\", \"key\": \"admin\", \"relation\": \"exists\"}" +
                "  ],":"")
                +   "\"buttons\": [{\"id\": \"okId\", \"action\": \"OK\", \"text\": \"OK\", \"icon\":\"https://www.123-stickers.com/6579-6950-thickbox/sticker-toad-youpi.jpg\"} ],"
                +   "\"contents\": {\"en\": \""+englishMessage + "\",\"fr\": \""+frenchMessage + "\"},"
                +   "\"headings\": {\"en\": \""+englishTitle+"\",\"fr\": \""+frenchTitle+"\"},"
                +   "\"large_icon\": \"https://www.123-stickers.com/6579-6950-thickbox/sticker-toad-youpi.jpg\""
                + "}";

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    public void sendPushNotification(Long userId, String englishTitle, String frenchTitle, String englishMessage, String frenchMessage ) {
        sendPushNotification(userId,englishTitle,frenchTitle,englishMessage,frenchMessage,false);
    }

    public void switchTagAdmin(Long userId, boolean isAdmin ) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/apps/aa9cbc7f-2910-4afe-9cec-ac799f760b8f/users/"+userId+"");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic \u003cNTZkMWY5NGMtOWRiYy00NjRiLTk5ZTYtYmJiNzIzZmU2YThj\u003e");
            con.setRequestMethod("PUT");



            String strJsonBody = "{"
                +   "\"tags\": {\"admin\": \""+(isAdmin?isAdmin:"")+"\"}"
                + "}";

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }


}

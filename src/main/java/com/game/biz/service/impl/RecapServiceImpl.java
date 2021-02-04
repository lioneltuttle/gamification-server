package com.game.biz.service.impl;

import com.game.biz.model.Resultat;
import com.game.biz.model.enumeration.BadgeType;
import com.game.biz.service.*;
import com.game.biz.service.dto.RecapDTO;
import com.game.biz.service.dto.UserDTO;
import com.game.repository.biz.ResultatRepository;
import com.game.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecapServiceImpl implements RecapService {

    public static final int DEFAULT_SCH_RATE = 43200000;
    private final Logger log = LoggerFactory.getLogger(RecapServiceImpl.class);

    private final BadgeMasterService badgeMasterService;

    private final BadgeLegendService badgeLegendService;

    private final UserService userService;

    private final ResultatService resultatService;

    private final NotificationService notificationService;

    private final PresentService presentService;

    public RecapServiceImpl(ResultatRepository resultatRepository, BadgeMasterService badgeMasterService, BadgeLegendService badgeLegendService, UserService userService, ResultatService resultatService, NotificationService notificationService, PresentService presentService) {
        this.badgeMasterService = badgeMasterService;
        this.badgeLegendService = badgeLegendService;
        this.userService = userService;
        this.resultatService = resultatService;
        this.notificationService = notificationService;
        this.presentService = presentService;
    }

    public List<RecapDTO> fullRecap(){
        List<RecapDTO> retour = new ArrayList<>();

        List<UserDTO> users = userService.findAllUsers();

        users.stream().forEach(userDTO -> {
            retour.add( new RecapDTO(userDTO,
            resultatService.findByUserId(userDTO.getId()),
                (int)badgeMasterService.getNbBadgesMaster(userDTO.getId()),
                (int)badgeLegendService.getNbBadgesLegend(userDTO.getId()),
                (int)presentService.getNbConsumedPresents(userDTO.getId()),
                (int)presentService.getNbPendingPresents(userDTO.getId())
            ) );
        });

        return retour;
    }

    public List<RecapDTO> adminRecap(){
        List<RecapDTO> retour = new ArrayList<>();

        List<UserDTO> users = userService.findAllAdmins();

        users.stream().forEach(userDTO -> {
            retour.add( new RecapDTO(userDTO,
                resultatService.findByUserId(userDTO.getId()),
                (int)badgeMasterService.getNbBadgesMaster(userDTO.getId()),
                (int)badgeLegendService.getNbBadgesLegend(userDTO.getId()),
                (int)presentService.getNbConsumedPresents(userDTO.getId()),
                (int)presentService.getNbPendingPresents(userDTO.getId())) );
        });

        return retour;
    }

    public List<RecapDTO> inactiveRecap(){
        List<RecapDTO> retour = new ArrayList<>();

        List<UserDTO> users = userService.findAllInactiveUsers();

        users.stream().forEach(userDTO -> {
            retour.add( new RecapDTO(userDTO,
                resultatService.findByUserId(userDTO.getId()),
                (int)badgeMasterService.getNbBadgesMaster(userDTO.getId()),
                (int)badgeLegendService.getNbBadgesLegend(userDTO.getId()),
                (int)presentService.getNbConsumedPresents(userDTO.getId()),
                (int)presentService.getNbPendingPresents(userDTO.getId())) );
        });

        return retour;
    }

    @Scheduled(fixedRate = DEFAULT_SCH_RATE)
    public void notifyPointsAndPro() {
        Calendar calendar = Calendar.getInstance();
        int maximumDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int deltaDays = maximumDaysInMonth - currentDay + 1;
        boolean isMorning = calendar.get(Calendar.AM) == 1;
        if (deltaDays % 2 == 1 && deltaDays <= 5 && isMorning) {
            List<UserDTO> users = userService.findAllUsers();
            users.stream().forEach(user1 -> {

                List<Resultat> results = resultatService.findByUserId(user1.getId());

                BadgeType badgeType = null;
                int nbPoints = 0;
                for (Resultat r : results) {
                    if (r.getPoints() > nbPoints) {
                        nbPoints = r.getPoints();
                        badgeType = r.getCategorie();
                    }
                }

                if (badgeType != null) {
                    int delta = badgeType.name().equals("R2") ? 20 - nbPoints : 10 - nbPoints;
                    if (delta <= 3) {
                        notificationService.sendPushNotification(user1.getId(), "Yes you can!", "Vous y êtes presque!",
                            delta + " points remaining for the category : " + badgeType.name() + ". Only " +deltaDays+ " day"+(deltaDays!=1?"s":"")+ "remaining to earn the corresponding badge!", "Plus que " + delta + " points à remporter dans la catégorie : " + badgeType.name() + ". Il ne vous reste que "+deltaDays+" jour"+(deltaDays!=1?"s":"")+" pour gagner le badge correspondant!");
                    }
                }

                int nbBadges = results.stream().map(e -> e.getNbBadges())
                    .reduce(0,Integer::sum);
                if (nbBadges==2) {
                        notificationService.sendPushNotification(user1.getId(), "Yes you can!", "Vous y êtes presque!",
                            "Only 1 badge PRO remaining! Only " +deltaDays+ " day"+(deltaDays!=1?"s":"")+ "remaining transform your Pro badges into Master badges!", "Il ne vous reste qu'un badge pro à obtenir! Il ne vous reste que "+deltaDays+" jour"+(deltaDays!=1?"s":"")+" avant la réinitialisation des badges pro!");
                }
                else if(nbBadges >=3){
                    notificationService.sendPushNotification(user1.getId(), "Don't forget your badges!", "N'oubliez pas de convertir vos badges!",
                        "Congratulations! You have enough Pro Badges to get a Master Badge! Only " +deltaDays+ " day"+(deltaDays!=1?"s":"")+ "remaining to transform your Pro badges into a Master badge!", "Félicitations! vous pouvez désormais transformer vos badges Pro en Badge Master! Il ne vous reste que "+deltaDays+" jour"+(deltaDays!=1?"s":"")+" avant la réinitialisation des badges Pro!");

                }
            });
        }
    }


    @Scheduled(fixedRate = DEFAULT_SCH_RATE)
    public void notifyBadgeMaster() {
        Calendar calendar = Calendar.getInstance();
        int maximumDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);
        int deltaDays = maximumDaysInMonth - currentDay + 1;
        boolean isMorning = calendar.get(Calendar.AM) == 1;
        if (deltaDays % 3 == 1 && deltaDays <= 15 && isMorning && currentMonth%3==2 ) {
            List<UserDTO> users = userService.findAllUsers();
            users.stream().forEach(user1 -> {
                long masters = badgeMasterService.getNbBadgesMaster(user1.getId());
                if(masters == 1){
                    notificationService.sendPushNotification(user1.getId(), "Yes you can!", "Vous y êtes presque!",
                        "Only 1 badge MASTER remaining! Only " +deltaDays+ " day"+(deltaDays!=1?"s":"")+ "remaining to transform your Master badges into Legend badges!", "Il ne vous reste qu'un badge Master à obtenir pour pouvoir prétendre à un badge Légende! Il ne vous reste que "+deltaDays+" jour"+(deltaDays!=1?"s":"")+" avant la réinitialisation des badges Master!");
                }
                else if(masters >=2){
                    notificationService.sendPushNotification(user1.getId(), "Don't forget your badges!", "N'oubliez pas de convertir vos badges!",
                        "Congratulations! You have enough Master Badges to get a Legend Badge! Only " +deltaDays+ " day"+(deltaDays!=1?"s":"")+ "remaining to transform your Master badges into a Legend badge!", "Félicitations! vous pouvez désormais transformer vos badges Master en Badge Légende! Il ne vous reste que "+deltaDays+" jour"+(deltaDays!=1?"s":"")+" avant la réinitialisation des badges Master!");

                }
            });
        }
    }

    @Scheduled(fixedRate = DEFAULT_SCH_RATE)
    public void notifyBadgeLegend() {
        Calendar calendar = Calendar.getInstance();
        int maximumDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);
        int deltaDays = maximumDaysInMonth - currentDay + 1;
        boolean isMorning = calendar.get(Calendar.AM) == 1;
        if (deltaDays % 5 == 1 && deltaDays <= 30 && isMorning && currentMonth==calendar.getActualMaximum(Calendar.MONTH)) {
            List<UserDTO> users = userService.findAllUsers();
            users.stream().forEach(user1 -> {
                long legends = badgeLegendService.getNbBadgesLegend(user1.getId());
                if(legends == 2){
                    notificationService.sendPushNotification(user1.getId(), "Yes you can!", "Vous y êtes presque!",
                        "Only 1 badge LEGEND remaining! Only " +deltaDays+ " day"+(deltaDays!=1?"s":"")+ "remaining to transform your Legend badges into a gift!", "Il ne vous reste qu'un badge Legend à obtenir pour pouvoir prétendre à une récompense! Il ne vous reste que "+deltaDays+" jour"+(deltaDays!=1?"s":"")+" avant la réinitialisation des badges Legend!");
                }
                else if(legends >=3){
                    notificationService.sendPushNotification(user1.getId(), "Don't forget your badges!", "N'oubliez pas de réclamer votre récompense!",
                        "Congratulations! You have enough Legend Badges to get a gift! Only " +deltaDays+ " day"+(deltaDays!=1?"s":"")+ "remaining to transform your Legend badges into a gift!", "Félicitations! vous pouvez désormais transformer vos badges Légende en récompense! Il ne vous reste que "+deltaDays+" jour"+(deltaDays!=1?"s":"")+" avant la réinitialisation des badges Legend!");
                }
            });
        }
    }



    @Scheduled(fixedRate = DEFAULT_SCH_RATE)
    public void resetPointsAndBadges() {
        Calendar calendar = Calendar.getInstance();
        int maximumDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);
        int deltaDays = maximumDaysInMonth - currentDay + 1;
        boolean isMorning = calendar.get(Calendar.AM) == 1;
             if (isMorning && currentDay == calendar.getActualMinimum(Calendar.DAY_OF_MONTH)) {

            // it is the first day of the month. We can reinitialize the points and badges pro to 0
            resultatService.resetMonthPoints();
            resultatService.findAll().stream().forEach(resultat -> resultat.setNbBadges(0));
            resultatService.findAll().stream().forEach(e -> resultatService.save(e));

            List<UserDTO> users = userService.findAllUsers();
            users.stream().forEach(user1 -> {
                if (currentMonth % 3 == 0) {
                    // reset master badges
                    badgeMasterService.findAll().stream().forEach(b -> badgeMasterService.delete(b.getId()));
                }
                if (currentMonth == 0) {
                    badgeLegendService.findAll().stream().forEach(b -> badgeLegendService.delete(b.getId()));
                }
            });
        }
    }
}

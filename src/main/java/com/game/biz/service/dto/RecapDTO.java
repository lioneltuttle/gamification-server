package com.game.biz.service.dto;

import com.game.biz.model.Resultat;
import com.game.biz.model.enumeration.BadgeType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecapDTO {

    private UserDTO user;

    private Map<BadgeType, Integer> badgesPro = new HashMap<>();

    private Integer badgeMaster;

    private Integer badgeLegend;

    private boolean shouldReceivePresent;


    public RecapDTO(UserDTO user , List<Resultat> resultats, int badgeLegend, int badgeMaster){
        this.user = user;
        resultats.stream().forEach(resultat ->  badgesPro.put(resultat.getCategorie(), resultat.getNbBadges()) );
        this.badgeMaster = badgeMaster;
        this.badgeLegend = badgeLegend;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Map<BadgeType, Integer> getBadgesPro() {
        return badgesPro;
    }

    public void setBadgesPro(Map<BadgeType, Integer> badgesPro) {
        this.badgesPro = badgesPro;
    }

    public Integer getBadgeMaster() {
        return badgeMaster;
    }

    public void setBadgeMaster(Integer badgeMaster) {
        this.badgeMaster = badgeMaster;
    }

    public Integer getBadgeLegend() {
        return badgeLegend;
    }

    public void setBadgeLegend(Integer badgeLegend) {
        this.badgeLegend = badgeLegend;
    }

    public boolean isShouldReceivePresent() {
        return shouldReceivePresent;
    }

    public void setShouldReceivePresent(boolean shouldReceivePresent) {
        this.shouldReceivePresent = shouldReceivePresent;
    }
}

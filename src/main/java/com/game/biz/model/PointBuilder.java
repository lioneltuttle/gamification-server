package com.game.biz.model;

public class PointBuilder {

    public static Point createPointFromOther(Point origin){
        Point result = new Point();
        result.setCategorie(origin.getCategorie());
        result.setUserId(origin.getUserId());
        result.setDate(origin.getDate());
        result.setNbPoints(origin.getNbPoints());
        result.setId(origin.getId());
        return result;
    }

}

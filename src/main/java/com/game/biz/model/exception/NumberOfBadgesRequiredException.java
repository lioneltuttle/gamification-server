package com.game.biz.model.exception;

public class NumberOfBadgesRequiredException extends Exception {

    public NumberOfBadgesRequiredException(){
        super();
    }

    public NumberOfBadgesRequiredException(String message){
        super(message);
    }
}

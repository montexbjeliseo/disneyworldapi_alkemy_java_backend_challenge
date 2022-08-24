package com.mtx.disneyworld.dto;

public class MessageDto {
    private String msg;
    private String action;
    public MessageDto(String a, String m){
        this.msg = m;
        this.action = a;
    }
}
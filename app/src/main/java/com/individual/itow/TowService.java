package com.individual.itow;

public class TowService {
    private String nameTow;
    private String emailTow;
    private String cityTow;
    private String phoneTow;

    public TowService() {
    }

    public TowService(String nameTow, String emailTow, String cityTow, String phoneTow) {
        this.nameTow = "Име: " + nameTow;
        this.emailTow = "Е-адреса: " + emailTow;
        this.cityTow = "Град: " + cityTow;

        this.phoneTow = "Телефонски број: " + phoneTow;

    }

    public String getNameTow() {
        return nameTow;
    }

    public String getEmailTow() {
        return emailTow;
    }

    public String getCityTow() {
        return cityTow;
    }

    public String getPhoneTow() {
        return phoneTow;
    }
}

package com.example.dentalmanagementd2.business.service;

public enum TypeOfCheckupServiceFactory {

    TYPE_OF_CHECKUP_SERVICE(new TypeOfCheckupService()), ;
    private TypeOfCheckupServiceLocal typeOfCheckupService;

    TypeOfCheckupServiceFactory(TypeOfCheckupServiceLocal typeOfCheckupsService){
        this.typeOfCheckupService = typeOfCheckupsService;
    }

    public TypeOfCheckupServiceLocal getTypeOfCheckupsService(){
        return typeOfCheckupService;
    }
}


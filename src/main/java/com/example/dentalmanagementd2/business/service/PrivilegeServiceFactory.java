package com.example.dentalmanagementd2.business.service;

public enum PrivilegeServiceFactory {
    PRIVILEGE_SERVICE(new PrivilegeService()), ;

    private PrivilegeServiceLocal privilegeService;

    PrivilegeServiceFactory(PrivilegeServiceLocal privilegeService){
        this.privilegeService = privilegeService;


    }
    public PrivilegeServiceLocal getPrivilegeService(){
        return privilegeService;
    }
}


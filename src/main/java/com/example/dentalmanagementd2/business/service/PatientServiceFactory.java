package com.example.dentalmanagementd2.business.service;

public enum PatientServiceFactory {

    SERVICE(new PatientService());

    private PatientService patientService;

    PatientServiceFactory(PatientService patientService){
        this.patientService = patientService;
    }

    public PatientService getPatientService(){
        return patientService;
    }
}


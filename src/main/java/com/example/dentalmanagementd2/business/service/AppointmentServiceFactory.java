package com.example.dentalmanagementd2.business.service;

public enum AppointmentServiceFactory {
    APPOINTMENT_SERVICE(new AppointmentService()), ;
    private AppointmentService appointmentService;

    AppointmentServiceFactory(AppointmentService appointmentService) {
        this.appointmentService =  appointmentService;
    }

    public AppointmentServiceLocal getAppointmentService() {
        return (AppointmentServiceLocal) appointmentService;
    }

}



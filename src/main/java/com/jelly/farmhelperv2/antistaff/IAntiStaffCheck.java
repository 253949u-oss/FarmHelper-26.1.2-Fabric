package com.jelly.farmhelperv2.antistaff;

public interface IAntiStaffCheck {
    String getCheckName();
    boolean isViolating();
    String getReason();
    void tick();
}
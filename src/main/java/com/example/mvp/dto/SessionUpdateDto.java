package com.example.mvp.dto;


import com.example.mvp.database_table.Session;
import jakarta.validation.constraints.NotNull;

public class SessionUpdateDto {
    @NotNull(message = "Session ID is required")
    private Long sessionId;

    @NotNull(message = "Status is required")
    private Session.SessionStatus status;

    private String sessionNotes;
    private String meetingLink;
    private String location;

    public SessionUpdateDto() {}

    // Getters and Setters
    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public Session.SessionStatus getStatus() { return status; }
    public void setStatus(Session.SessionStatus status) { this.status = status; }

    public String getSessionNotes() { return sessionNotes; }
    public void setSessionNotes(String sessionNotes) { this.sessionNotes = sessionNotes; }

    public String getMeetingLink() { return meetingLink; }
    public void setMeetingLink(String meetingLink) { this.meetingLink = meetingLink; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}

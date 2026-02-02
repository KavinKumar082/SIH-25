package com.example.backend.dto.response.dashboard;

public class AgingBucketDTO {

    private String label;   // "0-30", "31-60", etc
    private long value;

    public AgingBucketDTO(String label, long value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public long getValue() {
        return value;
    }
}

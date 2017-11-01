package com.test.assignment.ServiceCall.SampleDataDetails;

/**
 * Created by Keyur Tailor on 01-Nov-17.
 */

public class SampleDataFailureEvent {
    boolean successful;
    String errorMessageDisplay;

    public SampleDataFailureEvent(boolean successful, String errorMessageDisplay) {
        this.successful = successful;
        this.errorMessageDisplay = errorMessageDisplay;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getErrorMessageDisplay() {
        return errorMessageDisplay;
    }
}

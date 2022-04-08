package com.groep3.pathedeapp.presentation;

import static org.junit.Assert.*;

import com.groep3.pathedeapp.ValidationTools;

import org.junit.Test;

public class LoginActivityTest {
    ValidationTools mValidationTools = new ValidationTools();

    @Test
    public void inputFieldIsNotEmptyReturnFalse() {
        assertFalse(mValidationTools.isInputFieldEmpty("input"));
    }

    @Test
    public void inputFieldIsEmptyReturnTrue() {
        assertTrue(mValidationTools.isInputFieldEmpty(""));
    }
}
package com.example.myviewmodel;

import junit.framework.TestCase;

import net.bytebuddy.asm.Advice;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MainViewModelTest {

    private String width = "3";
    private String widthError = "3.5";
    private String height = "2";
    private String length = "5";
    private int expectedResult = 30;
    private String exceptionMsg = "For input string: \"" + widthError + "\"";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private MainViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewModel = new MainViewModel();
    }

    @Test
    public void calculate(){
        viewModel.calculate(width, height, length);
        TestCase.assertEquals(expectedResult, viewModel.result);
    }

    @Test
    public void calculateErrorInputType() throws NumberFormatException{
        thrown.expect(NumberFormatException.class);
        thrown.expectMessage(exceptionMsg);

        viewModel.calculate(widthError, height, length);
    }
}
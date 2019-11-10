package com.example.myunittesting;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @Mock
    private MainViewModel mainViewModel;

    @Mock
    private CuboidModel cuboidModel;

    private double dummyVolume = 504.0;
    private double dummyCircum = 2016.0;
    private double dummySurface = 396.0;
    private double dummyLength = 12.0;
    private double dummyWidth = 7.0;
    private double dummyHeight = 6.0;
    private double volumeRes;
    private double circumRes;
    private double surfaceRes;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        cuboidModel = new CuboidModel();
        mainViewModel = new MainViewModel(cuboidModel);
    }

    @Test
    public void save() {
        mainViewModel.save(dummyLength, dummyWidth, dummyHeight);
    }

    @Test
    public void getCircumference() {
        save();
        circumRes = mainViewModel.getCircumference();
        System.out.println(circumRes);
        TestCase.assertEquals(dummyCircum, circumRes);
    }

    @Test
    public void getSurfaceArea() {
        save();
        surfaceRes = mainViewModel.getSurfaceArea();
        System.out.println(surfaceRes);
        TestCase.assertEquals(dummySurface, surfaceRes);
    }

    @Test
    public void getVolume() {
        save();
        volumeRes = mainViewModel.getVolume();
        System.out.println(volumeRes);
        TestCase.assertEquals(dummyVolume, volumeRes);
    }
}
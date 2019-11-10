package com.example.myunittesting;

public class MainViewModel {

    private CuboidModel cuboidModel;

    MainViewModel(CuboidModel cuboidModel) { this.cuboidModel = cuboidModel; }

    void save(double l, double w, double h) {
        cuboidModel.save(l, w, h);
    }

    double getCircumference() {
        return cuboidModel.getCircumference();
    }

    double getSurfaceArea() {
        return cuboidModel.getSurfaceArea();
    }

    double getVolume() {
        return cuboidModel.getVolume();
    }
}

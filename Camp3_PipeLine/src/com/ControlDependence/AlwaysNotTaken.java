package com.ControlDependence;

public class AlwaysNotTaken implements branchController {
    @Override
    public boolean taken() {
        return false;
    }
}

package io.academy.backend.academy.entity;

import java.util.ArrayList;
import java.util.List;

public class Unit {
    private String name;
    private List<Resident> residents = new ArrayList<>();

    public Unit(String name) {
        this.name = name;
    }

    public int getResidentCount() {
        return this.residents.size();
    }

    public int getActiveResidentCount() {
        int count = 0;
        for (Resident resident : this.residents) {
            if (resident.hasLoggedIn()) {
                count++;
            }
        }
        return count;
    }

    public void addResident(Resident resident) {
        this.residents.add(resident);
    }

    public List<Resident> getResidents() {
        return this.residents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    @Override
    public String toString() {
        return "Unity{" +
                "name='" + name + '\'' +
                ", residentList=" + residents +
                '}';
    }
}

package io.academy.backend.academy.entity;

import io.academy.backend.academy.exceptions.DivisionByZeroException;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Community {

    @Id
    private String id;
    private String name;
    private String address;

    private HouseType typeOfHouse;

    private List<Unit> units = new ArrayList<>();


    private boolean deleted = false;

    public Community() {
    }

    public Community(String name, String address, HouseType houseType) {
        this.name = name;
        this.address = address;
        this.typeOfHouse = houseType;
    }


    public int totalResidents() {
        int totalResidents = 0;
        for (Unit unit : this.units) {
            totalResidents += unit.getResidentCount();
        }
        return totalResidents;
    }

    public int totalActiveResidents() {
        int totalActiveResidents = 0;
        for (Unit unit : this.units) {
            totalActiveResidents += unit.getActiveResidentCount();
        }
        return totalActiveResidents;
    }

    public double activeResidentsPercentage() {
        if (this.totalResidents() == 0) {
            throw new DivisionByZeroException("You cant get the percentage, the community hasn't any residents");
        }
        return (100 * ((double) this.totalActiveResidents() / this.totalResidents()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }


    public HouseType getTypeOfHouse() {
        return this.typeOfHouse;
    }


    public void setUnit(Unit unit) {
        this.units.add(unit);
    }

    public List<Unit> getUnits() {
        return this.units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Community{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", typeOfHouse=" + typeOfHouse +
                ", units=" + units +
                ", deleted=" + deleted +
                '}';
    }
}

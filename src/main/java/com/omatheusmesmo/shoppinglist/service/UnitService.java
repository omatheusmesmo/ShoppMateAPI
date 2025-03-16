package com.omatheusmesmo.shoppinglist.service;

import com.omatheusmesmo.shoppinglist.entity.Unit;
import com.omatheusmesmo.shoppinglist.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UnitService {

    private UnitRepository unitRepository;

    public Unit saveUnit(Unit unit){
        isUnitValid(unit);
        unitRepository.save(unit);
        return unit;
    }

    public void editUnit(Unit unit) {
        isUnitValid(unit);
        if (unitExists(unit)) {
            throw new NoSuchElementException("Unit not found");
        }
        saveUnit(unit);
    }

    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    public Optional<Unit> findUnitById(Long id){
        return unitRepository.findById(id);
    }

    public Optional<Unit> findUnitBySymbol(String symbol){
        return unitRepository.findBySymbol(symbol);
    }

    public Optional<Unit> findUnitByName(String name){
        return unitRepository.findByName(name);
    }

    public void removeUnit(Unit unit){
        unit.setDeleted(true);
        saveUnit(unit);
    }

    public void removeUnitById(Long id) {
        Optional<Unit> unit = findUnitById(id);
        Unit deletedUnit = unit.get();
        deletedUnit.setDeleted(true);
        saveUnit(deletedUnit);
    }

    private boolean unitExists(Unit unit){
        return unitRepository.existsById(unit.getId());
    }

    public void isUnitValid(Unit unit) {
        checkName(unit.getName());
        checkSymbol(unit.getSymbol());
    }

    private void checkName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("The unit name cannot be null!");
        } else if (name.isBlank()) {
            throw new IllegalArgumentException("Enter a valid unit name!");
        }
    }

    private void checkSymbol(String symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException("The unit symbol cannot be null!");
        } else if (symbol.isBlank()) {
            throw new IllegalArgumentException("Enter a valid unit symbol!");
        }
    }
}

package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.CarModel;

public interface CarService {
	CarModel addCar(CarModel car);

	void deleteCar(CarModel car);
	
	void updateCar(CarModel car, Long id);
	
	CarModel getCar(Long id);

	Optional<CarModel> getCarDetailById(Long id);
	List<CarModel> getAllCar();
}

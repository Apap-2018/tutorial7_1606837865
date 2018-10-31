package com.apap.tutorial7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.apap.tutorial7.model.*;
import com.apap.tutorial7.service.*;

@RestController
@RequestMapping("/car")
public class CarController {
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@GetMapping()
	private List<CarModel> getAllCar(){
		return carService.getAllCar();
	}
	
	@GetMapping(value = "/{carId}")
	private CarModel viewCar(@PathVariable("carId") long carId) {
		return carService.getCarDetailById(carId).get();
	}
	
	@DeleteMapping(value = "{carId}")
	private String deleteCar(@PathVariable("carId") long carId) {
		CarModel car = carService.getCarDetailById(carId).get();
		carService.deleteCar(car);
		return "car has been deleted";
	}
	
	@PutMapping(value="/{carId}")
	private String updateCarSubmit(@PathVariable(value="carId") long carId,
			@RequestParam("brand") String brand, 
			@RequestParam("type") String type, 
			@RequestParam("price") String price, 
			@RequestParam("amount") String amount, 
			@RequestParam("dealerId") String dealerId) {
		CarModel car = (CarModel) carService.getCarDetailById(carId).get();
		DealerModel dealer = (DealerModel) dealerService.getDealerDetailById(Long.parseLong(dealerId)).get();
		if(car.equals(null)) {
			return "Can't find your car";
		}
		car.setAmount(Integer.parseInt(amount));
		car.setBrand(brand);
		car.setDealer(dealer);
		car.setId(carId);
		car.setPrice(Long.parseLong(price));
		car.setType(type);
		carService.updateCar(car, carId);
		return "car update success";
	}
	
	
//	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
//	private String add(@PathVariable (value = "dealerId") Long dealerId, Model model) {
//		CarModel car = new CarModel();
//		DealerModel dealer  = dealerService.getDealerDetailById(dealerId).get();
//		ArrayList<CarModel> list = new ArrayList<CarModel>();
//		list.add(car);
//		dealer.setListCar(list);
//		
//		model.addAttribute("car", car);
//		model.addAttribute("title", "Add Car");
//		model.addAttribute("deal", dealer);
//		return "addCar";
//	}
//	
//	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST)
//	private String addCarSubmit(@ModelAttribute DealerModel dealer, Model model) {
//		DealerModel dealerNow = dealerService.getDealerDetailById(dealer.getId()).get();
//		for(CarModel car : dealer.getListCar()) {
//			car.setDealer(dealerNow);
//			carService.addCar(car);
//		}
//		model.addAttribute("title", "Add Successfull");
//		return "add";
//	}
//	
//	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params= {"addBaris"})
//	public String addBaris(@ModelAttribute DealerModel dealer, BindingResult bindingResult, Model model) {
//		if (dealer.getListCar() == null) {
//            dealer.setListCar(new ArrayList<CarModel>());
//        }
//		dealer.getListCar().add(new CarModel());
//		
//		model.addAttribute("deal", dealer);
//		return "addCar";
//	}
//	
//	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params={"removeBaris"})
//	public String removeBaris(@ModelAttribute DealerModel dealer, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
//	    final Integer rowId = Integer.valueOf(req.getParameter("removeBaris"));
//	    dealer.getListCar().remove(rowId.intValue());
//	    
//	    model.addAttribute("deal", dealer);
//	    return "addCar";
//	}
//	
//	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
//	private String delete(@ModelAttribute DealerModel dealer, Model model) {
//		for(CarModel car : dealer.getListCar()) {
//			carService.deleteCar(car);
//		}
//		model.addAttribute("title", "Delete Successfull");
//		return "delete";
//	}
//	
//	
////	@RequestMapping(value = "/car/delete/{dealerId}", method=RequestMethod.GET)
////	private String deleteCar(@PathVariable(value="dealerId") Long dealerId, Model model) {
////		return "deleteCar";
////	}
////
////	@RequestMapping(value = "/car/delete", method=RequestMethod.POST)
////	private String deleteCarSubmit(String carId, String idDealer) {
////		CarModel car = carService.getCarDetailById(Long.parseLong(carId)).get();
////		carService.deleteCar(car);
////		return "delete";
////	
////	}
//	
//	@RequestMapping(value = "/car/update/{id}", method = RequestMethod.GET)
//	private String updateCar(@PathVariable(value = "id") long id , Model model) {
//		CarModel cars = carService.getCar(id);
//		model.addAttribute("car", cars);
//		model.addAttribute("title", "Update Car");
//		return "updateCar";
//	}
//	
//	@RequestMapping(value = "/car/update/{id}" , method = RequestMethod.POST)
//	private String updateCarSubmit(@PathVariable(value = "id") long id, @ModelAttribute CarModel car, Model model) {
//		carService.updateCar(car, id);
//		model.addAttribute("title", "Update Successfull");
//		return "update";
//	}
}

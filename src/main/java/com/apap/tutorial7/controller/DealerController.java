	package com.apap.tutorial7.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.apap.tutorial7.model.*;
import com.apap.tutorial7.rest.DealerDetail;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.*;

@RestController
@RequestMapping("/dealer")
public class DealerController {

	@Autowired
	private DealerService dealerService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}
	
	@PostMapping(value = "/add")
	private DealerModel addDealerSubmit (@RequestBody DealerModel dealer) {
		return dealerService.addDealer(dealer);
	}
	
	@GetMapping(value = "/{dealerId}")
	private DealerModel viewDealer(@PathVariable("dealerId") long dealerId, Model model) {
		return dealerService.getDealerDetailById(dealerId).get();
	}
	
	@DeleteMapping(value = "/delete")
	private String deleteDealer(@RequestParam("dealerId") long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		dealerService.deleteDealer(dealer);
		return "Success";
	}
	
	@PutMapping(value = "/{id}")
	private String updateDealerSubmit(
			@PathVariable(value = "id") long id,
			@RequestParam("alamat") String alamat,
			@RequestParam("telp") String telp) {
		DealerModel dealer = (DealerModel) dealerService.getDealerDetailById(id).get();
		if(dealer.equals(null)) {
			return "couldn't find your dealer";
		}
		dealer.setAlamat(alamat);
		dealer.setNoTelp(telp);
		dealerService.updateDealer(id, dealer);
		return "update success";
	}
	
	@GetMapping()
	private List<DealerModel> viewAllDealer(Model model){
		return dealerService.getAllDealer();
	}
	
	@GetMapping(value = "/status/{dealerId}")
	private String getStatus(@PathVariable("dealerId") long dealerId) throws Exception{
		String path = Setting.dealerUrl + "/dealer?id=" + dealerId;
		return restTemplate.getForEntity(path, String.class).getBody();
	}
	
	@GetMapping(value = "/full/{dealerId}")
	private DealerDetail postStatus(@PathVariable ("dealerId") long dealerId) throws Exception{
		String path  = Setting.dealerUrl + "/dealer";
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		DealerDetail detail = restTemplate.postForObject(path, dealer, DealerDetail.class);
		return detail;
	}
	
		
//	@RequestMapping("/")
//	private String home(Model model) {
//		model.addAttribute("title", "Home");
//		return "home";
//	}
//	
//	@RequestMapping(value = "/dealer/add" , method = RequestMethod.GET)
//	private String add (Model model) {
//		model.addAttribute("dealer", new DealerModel());
//		model.addAttribute("title", "Add Dealer");
//		return "addDealer";
//	}
//	
//	@RequestMapping(value = "/dealer/add" , method = RequestMethod.POST)
//	private String addDealerSubmit(@ModelAttribute DealerModel dealer , Model model) {
//		dealerService.addDealer(dealer);
//		model.addAttribute("title", "Add Successfull");
//		return "add";
//	}
//	
//	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
//	private String view(String dealerId, Model model) {
//		DealerModel dealer = null;
//		List<CarModel> listCar = null;
//		if(dealerService.getDealerDetailById(Long.parseLong(dealerId)).isPresent()) {
//			dealer = dealerService.getDealerDetailById(Long.parseLong(dealerId)).get();
//			listCar = dealer.getListCar();
//			Collections.sort(listCar, priceComparator);
//		}
//		model.addAttribute("listCar", listCar);
//		model.addAttribute("deal", dealer);
//		model.addAttribute("dealId", dealerId);
//		model.addAttribute("title", "View Dealer");
//		return "viewDealer";
//	}
//	
//	@RequestMapping(value = "/dealer/delete", method =RequestMethod.GET)
//	private String delete(String dealerId, Model model) {
//		if(dealerService.getDealerDetailById(Long.parseLong(dealerId)).isPresent()) {
//			DealerModel dealer = dealerService.getDealerDetailById(Long.parseLong(dealerId)).get();
//			if (dealer.getListCar().isEmpty()) {
//				dealerService.deleteDealer(dealer);
//				model.addAttribute("title", "Delete Berhasil");
//				return "delete";
//			}
//			else {
//				List<CarModel> listCar = dealer.getListCar();
//				for (CarModel car : listCar) {
//					carService.deleteCar(car);
//					dealerService.deleteDealer(dealer);
//					model.addAttribute("title", "Delete Berhasil");
//					return "delete";
//				}
//			}
//		}
//		return "error";
//	}
//	
//	@RequestMapping(value="/dealer/update/{dealerId}", method = RequestMethod.GET)
//	private String updateDealer(@PathVariable(value="dealerId") Long dealerId, Model model) {
//		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
//		model.addAttribute("deal", dealer);
//		model.addAttribute("title", "Update Dealer");
//		return "update-dealer";
//	}
//	
//	@RequestMapping(value="/dealer/update/{dealerId}", method = RequestMethod.POST)
//	private String update(@PathVariable(value="dealerId") Long dealerId, @ModelAttribute Optional<DealerModel> deals, Model model) {
//		if(deals.isPresent()) {
//			dealerService.updateDealer(deals, dealerId);
//			model.addAttribute("title", "Update Success");
//			return "update";
//		}else {
//			model.addAttribute("title", "Error");
//			return "error";
//		}
//		
//	}
//	
//	@RequestMapping(value="/dealer/view-all", method= RequestMethod.GET)
//	private String viewAllCar(Model model) {
//		List<DealerModel> listDealer = dealerService.getAllDealer();
//		
//		model.addAttribute("listDealer", listDealer);
//		model.addAttribute("title", "View All Dealer");
//		return "viewAll";
//	}
//
//	CarModel car;
//	public static Comparator<CarModel> priceComparator = new Comparator<CarModel>() {
//		public int compare(CarModel o1, CarModel o2) {
//			Long price1 = o1.getPrice();
//			Long price2 = o2.getPrice();
//			return price1.compareTo(price2);
//		}
//	}
}

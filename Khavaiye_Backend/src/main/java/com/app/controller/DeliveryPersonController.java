package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.Credentials;
import com.app.dtos.CustomerDto;
import com.app.dtos.DeliveryPersonDto;
import com.app.dtos.DeliveryPersonHomePageDto;
import com.app.dtos.DeliveryPersonSignUpDto;
import com.app.dtos.KhavaiyeResponse;
import com.app.dtos.OrdersDto;
import com.app.entities.DeliveryPerson;
import com.app.services.DeliveryPersonService;
import com.app.services.OrdersService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/v1/")
public class DeliveryPersonController {
	
	@Autowired
	private DeliveryPersonService deliveryPersonService;
	
	@Autowired
	private OrdersService ordersService;
	
//	@GetMapping("/deliveryperson")
//	public List<DeliveryPerson> findAllDeliveryPerson() {
//		return deliveryPersonService.findAllDeliveryPerson();
//	}
	
//	@GetMapping("/deliverypersontest")
//	public ResponseEntity<HungerBuzzResponse> findAllDeliveryPersontest() {
//		return HungerBuzzResponse.success(deliveryPersonService.findAllDeliveryPerson());
//	}
	
	@PostMapping("/deliveryperson/signin")
	public ResponseEntity<KhavaiyeResponse> signIn(@RequestBody Credentials cred) {
		DeliveryPersonDto deliveryPersonDto = deliveryPersonService.findDeliveryPersonByEmailAndPassword(cred);
		if(deliveryPersonDto == null)
			return KhavaiyeResponse.error("Couldn't find Delivery Person with that credentials");
		return KhavaiyeResponse.success(deliveryPersonDto);
	}
	
	@GetMapping("/deliverypersonhomepage/{id}")
	public ResponseEntity<KhavaiyeResponse> findDeliveryPersonHomePageDetails(@PathVariable("id") int id){
		DeliveryPersonHomePageDto deliveryPersonDto = ordersService.getdeliveryPersonHomePageDtoById(id);
		if(deliveryPersonDto == null)
			return KhavaiyeResponse.error("Couldn't find Delivery Person Details with that id");
		return KhavaiyeResponse.success(deliveryPersonDto);
	}
	
	@PostMapping("/deliveryperson/{orderId}/{status}")
	public ResponseEntity<KhavaiyeResponse> setStatusByOrder(@PathVariable("orderId") int orderId, @PathVariable("status") String status) {
		boolean updateStatus = ordersService.setStatusForOrder(orderId, status);
		if(!updateStatus)
			return KhavaiyeResponse.error("Couldn't update status for order");
		return KhavaiyeResponse.success("Order status updated");
	}
	
	@GetMapping("/orders/deliveryperson/{id}")
	public ResponseEntity<KhavaiyeResponse> getAllOrdersbyCustomerId(@PathVariable("id") int deliveryPersonId) {
		List<DeliveryPersonHomePageDto> dphpDtoList = ordersService.findAllOrdersByDeliveryPerson(deliveryPersonId);
		if(dphpDtoList == null || dphpDtoList.isEmpty())
			return KhavaiyeResponse.error("List empty!");
		return KhavaiyeResponse.success(dphpDtoList);
	}
	
	@PostMapping("/deliveryperson/arrivedorders/{deliverypersonId}")
	public ResponseEntity<KhavaiyeResponse> getArrivedOrders(@PathVariable("deliverypersonId") int deliverypersonId){
		String status = "arrived";
		List<DeliveryPersonHomePageDto> orders = ordersService.findArrivedordersByDeliverypersonIdAndStatus(deliverypersonId,status);
		if(orders == null || orders.isEmpty())
			return KhavaiyeResponse.error("No orders assigned");
		
		//List<OrdersDto>ordersDtoList = DaoToEntityConverter.ordersToOrdersDto(orders);
		return KhavaiyeResponse.success(orders);
		
	}
	
	@GetMapping("/deliveryperson/{deliverypersonId}/status/{status}")
	public ResponseEntity<KhavaiyeResponse> getOrders(@PathVariable("deliverypersonId") int deliverypersonId, @PathVariable("status") String status){
		
		List<DeliveryPersonHomePageDto> orders = ordersService.findArrivedordersByDeliverypersonIdAndStatus(deliverypersonId,status);
		if(orders == null || orders.isEmpty())
			return KhavaiyeResponse.error("No orders assigned");
		
		return KhavaiyeResponse.success(orders);
	}
	
	@PostMapping("/deliveryperson/signup")
	public ResponseEntity<KhavaiyeResponse> deliveryPersonSignUp(@RequestBody DeliveryPersonSignUpDto deliveryPersonSignUpDto) {
		boolean status = deliveryPersonService.addDeliveryPerson(deliveryPersonSignUpDto);
		if(status)
			return KhavaiyeResponse.success("Delivery Person Added");
		return KhavaiyeResponse.error("Delivery person could not be added");
	}
	
}

package telran.cars.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.cars.dto.CarDto;
import telran.cars.dto.ModelDto;
import telran.cars.dto.OwnerDto;
import telran.cars.service.ICars;

@RestController
public class CarsController
{
	@Autowired
	ICars service;
	
	@PostMapping("/car")
	public boolean addCar(@RequestBody CarDto carDto)
	{
		return service.addCar(carDto);
	}

	@PostMapping("/model")
	public boolean addModel(@RequestBody ModelDto modelDto)
	{
		return service.addModel(modelDto);
	}

	@PostMapping("/owner")
	public boolean addOwner(@RequestBody OwnerDto ownerDto)
	{
		return service.addOwner(ownerDto);
	}

	@GetMapping("/car/{regNumber}")//localhost:8080/car/1111
	public CarDto getCar(@PathVariable long regNumber)
	{
		return service.getCar(regNumber);
	}

	@GetMapping("/model/{name}")
	public ModelDto getModel(@PathVariable("name") String modelName)
	{
		return service.getModel(modelName);
	}

	@GetMapping("/owner/{ownerId}")
	public OwnerDto getOwner(@PathVariable int ownerId)
	{
		return service.getOwner(ownerId);
	}

	@GetMapping("/owners/car/{regNumber}")
	public List<OwnerDto> getCarOwners(@PathVariable long regNumber)
	{
		return service.getCarOwners(regNumber);
	}

	@GetMapping("/cars/owner/{ownerId}")
	public List<CarDto> getCarsByOwner(@PathVariable int ownerId)
	{
		return service.getCarsByOwner(ownerId);
	}

	@GetMapping("/cars/model/{modelName}")
	public List<CarDto> getCarsByModel(@PathVariable String modelName)
	{
		return service.getCarsByModel(modelName);
	}

	@DeleteMapping("/model/{modelName}")
	public boolean removeModel(@PathVariable String modelName)
	{
		return service.removeModel(modelName);
	}

}

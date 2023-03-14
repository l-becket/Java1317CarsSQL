package telran.cars.service;

import java.time.LocalDate;
import java.util.List;

import telran.cars.dto.CarDto;
import telran.cars.dto.ColorCount;
import telran.cars.dto.ModelAge;
import telran.cars.dto.ModelCount;
import telran.cars.dto.ModelDto;
import telran.cars.dto.OwnerDto;

public interface ICars
{
	boolean addCar(CarDto carDto);
	boolean addModel(ModelDto modelDto);
	boolean addOwner(OwnerDto ownerDto);
	CarDto getCar(long regNumber);
	ModelDto getModel(String modelName);
	OwnerDto getOwner(int ownerId);
	
	List<OwnerDto> getCarOwners(long regNumber);
	List<CarDto> getCarsByOwner(int ownerId);
	List<CarDto> getCarsByModel(String modelName);
	boolean removeModel(String modelName);
	
//	HW
	List<CarDto> getCarsByYear(int year);
	List<CarDto> getCarsByOwnerAges(int ageFrom, int ageTo);
	List<OwnerDto> getOwnersByModel(String modelName);
	boolean removeOwner(int ownerId);
	
	List<ModelCount> getModelsCounts();
//	select m.modelNAme, count(*) from models m join cars c using(modelName) 
//	group by m.modelName;
	List<OwnerDto> getOwnersCarsAmount(int amount);
	List<ModelDto> getMostPopularModels();
	
//	HW
	List<ColorCount> getColorsCount();
	List<ModelAge> getModelsAvgOwnerAge();
	List<String> getMostPopularModelsPurchasedDatesBetweenOwnersAgeBetween(LocalDate fromDate, 
			LocalDate toDate, int fromAge, int toAge);
}

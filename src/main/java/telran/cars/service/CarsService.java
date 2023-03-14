package telran.cars.service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.cars.domain.entity.Car;
import telran.cars.domain.entity.Model;
import telran.cars.domain.entity.Owner;
import telran.cars.domain.repo.CarsRepository;
import telran.cars.domain.repo.ModelsRepository;
import telran.cars.domain.repo.OwnersRepository;
import telran.cars.dto.CarDto;
import telran.cars.dto.ColorCount;
import telran.cars.dto.EntityAlreadyExistsException;
import telran.cars.dto.EntityNotFoundException;
import telran.cars.dto.ModelAge;
import telran.cars.dto.ModelCount;
import telran.cars.dto.ModelDto;
import telran.cars.dto.OwnerDto;

@Service
public class CarsService implements ICars
{
	CarsRepository carsRepo;
	ModelsRepository modelsRepo;
	OwnersRepository ownersRepo;
	ModelMapper mapper;
	
	@Autowired
	public CarsService(CarsRepository carsRepo, ModelsRepository modelsRepo, 
			OwnersRepository ownersRepo, ModelMapper mapper)
	{
		super();
		this.carsRepo = carsRepo;
		this.modelsRepo = modelsRepo;
		this.ownersRepo = ownersRepo;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public boolean addCar(CarDto carDto)
	{
		if(carDto.getPurchaseDate().isAfter(LocalDate.now()))
			return false;
		if(carsRepo.existsById(carDto.getRegNumber()))
			throw new EntityAlreadyExistsException("Car with reg number " 
							+ carDto.getRegNumber() + " is already exists");
		
		Model model = modelsRepo.findById(carDto.getModelName())
				.orElseThrow(() -> new EntityNotFoundException("Model with name " 
						+ carDto.getModelName() + " is not found"));
		
		List<Owner> owners = carDto.getOwners().stream().map(o -> 
		ownersRepo.findById(o.getId())
			.orElseThrow(() -> new EntityNotFoundException("Owner with id "
						+ o.getId() + " is not found"))).toList();
				
		Car car = new Car(carDto.getRegNumber(), carDto.getColor(), carDto.getPurchaseDate(),
				model, owners);
		carsRepo.save(car);
		return true;
	}

	@Transactional
	@Override
	public boolean addModel(ModelDto modelDto)
	{
		if(modelsRepo.existsById(modelDto.getModelName()))
			throw new EntityAlreadyExistsException("Model " 
					+ modelDto.getModelName() + " is already exists");
		Model model = new Model(modelDto.getModelName(), modelDto.getVolume(), 
				modelDto.getCompany());
		modelsRepo.save(model);
		return true;
		
	}

	@Transactional
	@Override
	public boolean addOwner(OwnerDto ownerDto)
	{
		if(ownersRepo.existsById(ownerDto.getId()))
		throw new EntityAlreadyExistsException("Owner with id " 
					+ ownerDto.getId() + " is already exists");	
		
		Owner owner = new Owner(ownerDto.getId(), ownerDto.getName(), 
				ownerDto.getBirthYear(), new ArrayList<>());
		ownersRepo.save(owner);
		return true;
	}

	@Override
	public CarDto getCar(long regNumber)
	{
		Car car = carsRepo.findById(regNumber).orElseThrow(() ->
			new EntityNotFoundException("Car not found"));
		
		return mapper.map(car, CarDto.class);
	}

	private CarDto toCarDto(Car car)
	{
		return new CarDto(car.regNumber, car.color, car.purchaseDate, car.model.modelName,
				toListOwnerDto(car.owners));
	}

	@Override
	public ModelDto getModel(String modelName)
	{
		return toModelDto(modelsRepo.findById(modelName).orElseThrow(()->
				new EntityNotFoundException("Model not found")));
	}

	private ModelDto toModelDto(Model model) 
	{
		return new ModelDto(model.modelName, model.volume, model.company);
	}

	@Override
	public OwnerDto getOwner(int ownerId)
	{
		return toOwnerDto(ownersRepo.findById(ownerId).orElseThrow(()-> new EntityNotFoundException("Owner not found")));
	}

	@Override
	public List<OwnerDto> getCarOwners(long regNumber)
	{
		Car car = carsRepo.findById(regNumber)
				.orElseThrow(() -> new EntityNotFoundException("Car not found"));
		return toListOwnerDto(car.owners);
	}

	private OwnerDto toOwnerDto(Owner owner)
	{
		return new OwnerDto(owner.id, owner.name, owner.birthYear);
	}

	@Override
	public List<CarDto> getCarsByOwner(int ownerId)
	{
		Owner owner = ownersRepo.findById(ownerId)
				.orElseThrow(() -> new EntityNotFoundException("Owner not found"));
		List<Car> res = owner.cars;
		if(res == null || res.isEmpty())
			return new ArrayList<>();
		
		return toListCarDto(res);
	}

	private List<CarDto> toListCarDto(List<Car> res)
	{
		return res.stream().map(this::toCarDto).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<CarDto> getCarsByModel(String modelName)
	{
		List<Car> res = carsRepo.findByModelModelName(modelName);
		return toListCarDto(res);
	}

	@Override
	@Transactional
	public boolean removeModel(String modelName)
	{
		if(!modelsRepo.existsById(modelName))
			throw new EntityNotFoundException("Model not found");
		
		List<Car> list = carsRepo.findByModelModelName(modelName);
		list.forEach(c -> carsRepo.delete(c));
		modelsRepo.deleteById(modelName);
		return true;
	}

	@Override
	public List<CarDto> getCarsByYear(int year) 
	{
		LocalDate  from = LocalDate.ofYearDay(year, 1);
		LocalDate to = LocalDate.ofYearDay(year, year%4 == 0 ? 366 : 365);
		List<Car> list = carsRepo.findByPurchaseDateBetween(from, to);
		if(list == null || list.isEmpty())
			return new ArrayList<>();
		return toListCarDto(list);
	}

	@Transactional(readOnly = true)
	@Override
	public List<CarDto> getCarsByOwnerAges(int ageFrom, int ageTo) 
	{
		int birthYearFrom = LocalDate.now().minusYears(ageTo).getYear();
		int birthYearTo = LocalDate.now().minusYears(ageFrom).getYear();
		List<Owner> owners = ownersRepo.findByBirthYearBetween(birthYearFrom, birthYearTo);
		List<Car> list = owners.stream().map(o -> o.cars).flatMap(l ->l.stream()).toList();
		if(list == null || list.isEmpty())
			return new ArrayList<>();
		return toListCarDto(list);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<OwnerDto> getOwnersByModel(String modelName) 
	{
		if(!modelsRepo.existsById(modelName))
			throw new EntityNotFoundException("Model with name " 
						+ modelName + " is not found");
		List<Car> list = carsRepo.findByModelModelName(modelName);
		if(list == null || list.isEmpty())
			return new ArrayList<>();
		
		return list.stream().map(c -> toListOwnerDto(c.owners))//List<List<OwnerDto>>
				.flatMap(l -> l.stream()).distinct().toList();
	}

	private List<OwnerDto> toListOwnerDto(List<Owner> res) 
	{
			return res.stream().map(this::toOwnerDto).distinct().toList();
	}

//	@Transactional
//	@Override
//	public boolean removeOwner(int ownerId) 
//	{
//		if(!ownersRepo.existsById(ownerId))
//			throw new EntityNotFoundException("Owner with id " 
//						+ ownerId + "is not found");
//		List<Car> list = carsRepo.findByOwnerId(ownerId);
//		list.forEach(c -> carsRepo.delete(c));
//		ownersRepo.deleteById(ownerId);
//		return true;
//	}
	
	@Transactional
	@Override
	public boolean removeOwner(int ownerId) 
	{
		if(!ownersRepo.existsById(ownerId))
			throw new EntityNotFoundException("Owner with id " 
						+ ownerId + "is not found");
		ownersRepo.deleteById(ownerId);
		return true;
	}

	@Override
	public List<ModelCount> getModelsCounts()
	{
		return carsRepo.getModelsCounts();
	}

	@Override
	public List<OwnerDto> getOwnersCarsAmount(int amount)
	{
		List<Owner> list = ownersRepo.getOwnersCarsAmount(amount);
		return toListOwnerDto(list);
	}

	@Override
	public List<ModelDto> getMostPopularModels()
	{
		List<Model> list = modelsRepo.getMostPopularModels();
		return list.stream().map(this::toModelDto).toList();
	}

	@Override
	public List<ColorCount> getColorsCount()
	{
		return carsRepo.getColorsCount();
	}

	@Override
	public List<ModelAge> getModelsAvgOwnerAge()
	{
		return carsRepo.getModelsAvgOwnerAge();
	}

	@Override
	public List<String> getMostPopularModelsPurchasedDatesBetweenOwnersAgeBetween
	(LocalDate fromDate, LocalDate toDate, int fromAge, int toAge)
	{
		return modelsRepo.getMostPopularModelsPurchasedDatesBetweenOwnersAgeBetween
				(fromDate, toDate, Year.now().minusYears(fromAge).getValue(), 
						Year.now().minusYears(toAge).getValue());
	}
}
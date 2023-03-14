package telran.cars.domain.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.cars.domain.entity.Car;
import telran.cars.dto.CarDto;
import telran.cars.dto.ColorCount;
import telran.cars.dto.ModelAge;
import telran.cars.dto.ModelCount;

public interface CarsRepository extends JpaRepository<Car, Long>
{

	List<Car> findByModelModelName(String modelName);

	List<Car> findByPurchaseDateBetween(LocalDate from, LocalDate to);

	@Query("select model.modelName as modelName, count(*) as count from Car "
			+ "group by model.modelName")
	List<ModelCount> getModelsCounts();

	@Query("select color, count(*) as count from Car group by color")
	List<ColorCount> getColorsCount();

	@Query(value = "select m.model_name as modelName, avg(year(current_date) - birthYear) "
			+ "as age from owners join cars_owners "
			+ "using(id)) join cars using(reg_number) join models m using(model_name) "
			+ "group by m.model_name", nativeQuery = true)
	List<ModelAge> getModelsAvgOwnerAge();
}

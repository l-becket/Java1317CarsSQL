package telran.cars.domain.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.cars.domain.entity.Model;

public interface ModelsRepository extends JpaRepository<Model, String>
{
	@Query(value = "select * from models where model_name in (select model_name from cars "
			+ "group by model_name having count(*) = (select max(count) from (select count(*)"
			+ " as count from cars group by model_name)))", nativeQuery = true)
	List<Model> getMostPopularModels();

	@Query(value = "select m.model_name from cars join cars_owners using(reg_number) "
			+ "join owners using(id) join models m using(model_name) "
	        + " where birth_year between ?3 and ?4 and "
	        + "purchase_date between ?1 and ?2 group by "
	        + "model_name having count(*)=(select max(count) from"
	        + " (select count(*) as count from cars join cars_owners using(reg_number) "
	        + "join owners using(id) join models m using(model_name) "
	        + "where birth_year between ?3 and ?4 and "
	        + "purchase_date between ?1 and ?2 group by m.model_name))", nativeQuery = true)
		List<String> getMostPopularModelsPurchasedDatesBetweenOwnersAgeBetween(LocalDate fromDate, LocalDate toDate,
			int fromAge, int toAge);

}

package telran.cars.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.cars.domain.entity.Owner;

public interface OwnersRepository extends JpaRepository<Owner, Integer>
{

	List<Owner> findByBirthYearBetween(int birthYearFrom, int birthYearTo);

//	@Query("select owner from Owner owner where size(owner.cars)>=:a")
//	List<Owner> getOwnersCarsAmount(@Param("a") int amount);
	
	@Query("select owner from Owner owner where size(owner.cars)>=?1")
	List<Owner> getOwnersCarsAmount(int amount);

}

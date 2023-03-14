package telran.cars.domain.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "owners")
public class Owner
{
	@Id
	public int id;
	public String name;
	public int birthYear;
	@ManyToMany(mappedBy = "owners", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public List<Car> cars;
}

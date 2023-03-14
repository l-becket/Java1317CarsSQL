package telran.cars.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "models")
public class Model
{
	@Id
	public String modelName;
	public int volume;
	public String company;
	
//	@OneToMany(cascade = CascadeType.ALL)
//	public List<Car> cars;
}
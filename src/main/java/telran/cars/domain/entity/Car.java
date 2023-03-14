package telran.cars.domain.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "cars", indexes = {@Index(columnList = "modelName, color")})
public class Car
{
	@GeneratedValue
	@Id
	public long regNumber;
	@Column(name = "color", nullable = true, unique = false)
	public String color;
	public LocalDate purchaseDate;
	@ManyToOne
	@JoinColumn(name = "modelName")
	public Model model;
	@ManyToMany
	@JoinTable(
			name = "cars_owners",
			joinColumns = @JoinColumn(name = "regNumber"),
			inverseJoinColumns = @JoinColumn(name = "id"))
	public List<Owner> owners;
}
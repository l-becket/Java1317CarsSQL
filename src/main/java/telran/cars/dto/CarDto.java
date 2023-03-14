package telran.cars.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "regNumber")
@ToString
public class CarDto
{
	private long regNumber;
	private String color;
	private LocalDate purchaseDate;
	private String modelName;
	private List<OwnerDto> owners;
}

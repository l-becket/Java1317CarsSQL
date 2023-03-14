package telran.cars.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "modelName")
public class ModelDto
{
	private String modelName;
	private int volume;
	private String company;
}

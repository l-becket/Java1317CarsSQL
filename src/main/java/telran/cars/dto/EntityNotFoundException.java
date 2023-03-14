package telran.cars.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException
{
	public EntityNotFoundException()
	{
	}
	
	public EntityNotFoundException(String message)
	{
		super(message);
	}
}
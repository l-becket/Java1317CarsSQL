package telran.cars.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class EntityAlreadyExistsException extends RuntimeException
{
	public EntityAlreadyExistsException()
	{
	}
	
	public EntityAlreadyExistsException(String message)
	{
		super(message);
	}
}

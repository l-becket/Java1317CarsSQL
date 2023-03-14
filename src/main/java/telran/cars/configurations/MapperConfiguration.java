package telran.cars.configurations;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration
{
	@Bean
	public ModelMapper getModelMapper()
	{
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE)
		.setFieldMatchingEnabled(true).setMatchingStrategy(MatchingStrategies.LOOSE);
		return mapper;
	}
}

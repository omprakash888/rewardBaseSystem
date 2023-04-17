package com.card.task2.rewardbasesystem;

import com.card.task2.rewardbasesystem.utils.RewardRule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class RewardBaseSystemApplication {


	private static final String FILEPATH = "/home/omprakash/rewardBaseSystem/src/main/resources/rewardRule.json";
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(RewardBaseSystemApplication.class, args);
	}

	@Bean
	public  Map<String, List<RewardRule>> createObject() {

		File file = new File(FILEPATH);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, List<RewardRule>> reward;
		try {
			reward = mapper.readValue(file, new TypeReference<Map<String, List<RewardRule>>>() {
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return reward;
	}


}

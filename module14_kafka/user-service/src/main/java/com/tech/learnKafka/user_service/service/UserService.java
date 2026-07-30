package com.tech.learnKafka.user_service.service;

import com.tech.learnKafka.user_service.dto.CreateUserRequestDto;
import com.tech.learnKafka.user_service.entity.User;
import com.tech.learnKafka.event.UserCreatedEvent;
import com.tech.learnKafka.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {


    @Value("${kafka.topic.user-created-topic}")
    private String KAFKA_USER_CREATED_TOPIC;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, UserCreatedEvent> eventKafkaTemplate;

    public void createUser(CreateUserRequestDto createUserRequestDto) {
        User user = modelMapper.map(createUserRequestDto,User.class);
        User save = userRepository.save(user);

        UserCreatedEvent userCreatedEvent = modelMapper.map(save, UserCreatedEvent.class);
        eventKafkaTemplate.send(KAFKA_USER_CREATED_TOPIC,userCreatedEvent.getId(),userCreatedEvent);
    }
}

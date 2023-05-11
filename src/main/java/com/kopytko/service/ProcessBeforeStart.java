package com.kopytko.service;

import com.kopytko.dao.UserRepository;
import com.kopytko.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessBeforeStart {

    private final UserRepository userRepository;
    private final Logger log = LogManager.getLogger(this);

    @EventListener
    public void addUsers(ContextRefreshedEvent refreshedEvent) {
        this.userRepository.save(new User("Kopytko"));
        this.userRepository.save(new User("Solnichok"));
        this.userRepository.save(new User("Pryhodko"));
        this.userRepository.save(new User("Smith"));
        this.userRepository.save(new User("Maven"));
        log.info("Add users to DB");
    }
}

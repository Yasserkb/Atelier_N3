package com.gestion_patients;

import com.gestion_patients.entities.Patients;
import com.gestion_patients.repositories.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class GestionPatientsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionPatientsApplication.class, args);
    }


    //@Bean
    CommandLineRunner commandLineRunner(PatientsRepository patientsRepository){
        return  args -> {
            patientsRepository.save(new Patients(null,"ahmed",new Date(),true,251));
            patientsRepository.save(new Patients(null,"Yasser",new Date(),false,124));
            patientsRepository.save(new Patients(null,"Youssef",new Date(),false,202));
            patientsRepository.save(new Patients(null,"Badr",new Date(),true,510));


//            patientsRepository.findAll().forEach(p->{
//                System.out.println(p.getName());
//            });
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

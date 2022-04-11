package uz.pdp.corxona_telecom.download;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.corxona_telecom.entity.User;
import uz.pdp.corxona_telecom.security.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        @Value("${spring.jpa.hibernate.ddl-auto}")
        private  String ddl;

    @Override
    public void run(String... args) throws Exception {

        if (ddl.equals("create")){
            // user qo'shadi :
            User user = new User();
            user.setRole("company");
            user.setUsername("company");
            user.setPassword(passwordEncoder.encode("0706"));

            userRepository.save(user); // user save bo'ldi

            User user1 = new User();
            user1.setRole("admin");
            user1.setUsername("admin");
            user1.setPassword(passwordEncoder.encode("0706"));

            userRepository.save(user1); // user save bo'ldi
        }
    }
}

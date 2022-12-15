package org.example.service;

import org.example.model.ApplicationUser;
import org.example.model.ApplicationUserDetailsImpl;
import org.example.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ApplicationUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    ApplicationUserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = usersRepository.findByUsername(username).orElseGet(null);
        return ApplicationUserDetailsImpl.build(user);
    }

    public void deleteUser(Long idUser) {
        usersRepository.deleteById(idUser);
    }

    public ApplicationUser updateUser(Long idUser, ApplicationUser applicationUser) {
        Optional<ApplicationUser> userOptional = usersRepository.findById(idUser);
        if (userOptional.isPresent()) {
            final ApplicationUser result = userOptional.get();
            result.setEmail(applicationUser.getEmail());
            result.setPassword(applicationUser.getPassword());
            result.setUsername(applicationUser.getUsername());
            return usersRepository.save(result);
        } else {
            return null;
        }
    }
}

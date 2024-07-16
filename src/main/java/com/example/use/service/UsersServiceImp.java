package com.example.use.service;

import com.example.use.entity.Users;
import com.example.use.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImp implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;



    public Users saveUsers(Users users) {
        return usersRepository.save(users);
    }


    public List<Users> fetchAllUsers() {
        return usersRepository.findAll();
    }

    private AuthenticationManager authenticationManager;

    public Users getUsers(Long id) {
        Optional<Users> user = usersRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }


    public Users updateUsers(Users users) {
        Optional<Users> users1 = usersRepository.findById(users.getId());
        if (users1.isPresent()) {
            Users originalUsers = (Users) users1.get();
            originalUsers.setName(users.getName());
            originalUsers.setLastname(users.getLastname());
            originalUsers.setEmail(users.getEmail());
            originalUsers.setAddress(users.getAddress());
            originalUsers.setPassword(users.getPassword());
            return (Users) this.usersRepository.save(originalUsers);
        } else {
            return null;
        }
    }


    public boolean deleteUsersById(Long id) {
        try {
            usersRepository.deleteById(id);
            return true;
        } catch (Exception e) {

            throw new RuntimeException("Failed to delete Users: " + e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username);

    }


}








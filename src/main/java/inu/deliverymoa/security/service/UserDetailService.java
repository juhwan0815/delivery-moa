package inu.deliverymoa.security.service;

import inu.deliverymoa.common.exception.NotFoundException;
import inu.deliverymoa.user.domain.User;
import inu.deliverymoa.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static inu.deliverymoa.common.exception.NotFoundException.USER_NOT_FOUND;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User findUser = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(findUser.getRole())));

        CustomUserDetail userDetail = new CustomUserDetail(String.valueOf(findUser.getId()), "", authorities);
        userDetail.setUser(findUser);
        return userDetail;
    }
}

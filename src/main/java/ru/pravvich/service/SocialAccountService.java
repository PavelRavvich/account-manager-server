package ru.pravvich.service;

import org.springframework.stereotype.Service;
import ru.pravvich.web.social.SocialAccountRest;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocialAccountService {
    public SocialAccountRest getSocialAccountById(int id) {
        return new SocialAccountRest();
    }

    public List<SocialAccountRest> getListSocialAccount() {
        return new ArrayList<>();
    }
}

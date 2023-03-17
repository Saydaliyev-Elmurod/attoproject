package service;

import dto.Profile;
import enums.Status;
import lombok.Setter;
import repository.ProfileRepository;
import util.MD5;

import java.util.List;
@Setter
public class ProfileService {
    private ProfileRepository profileRepository;
    public void registration(String name,String surname,String phone,String password ) {
        //check
        if (password.length() < 6) {
            System.out.println("Iltimos password 6 yoki undan ortiq belgidan iborat bolsin");
            return;
        }
        // bu profile oldin qoshilganmi yoqmi tekshiramiz phone unique bolishi kerak
        Profile profile1 = profileRepository.getProfileByPhone(phone);

        if (profile1 != null) {
            System.out.println("Bu numerdan allaqachon royxatdan otilgan");
            return;
        }
        Profile profile = new Profile();
        profile.setPassword(MD5.getMd5Hash(password));
        profile.setName(name);
        profile.setSurname(surname);
        profile.setPhone(phone);
        profileRepository.registration(profile);
    }

    public Profile  login(String phone, String password) {
        //check
        if(password.length()<6){
            return null;
        }
        //.....
        return profileRepository.login(phone,password);

    }

    public List<Profile> profileList() {
         return profileRepository.profileList();
    }

    public void changeProfileStatus(String phone) {
        Profile profile = profileRepository.getProfileByPhone(phone);
        if (profile==null){
            System.out.println("Profile not found");
            return;
        }
        if (profile.getStatus().equals(Status.ACTIVE)){
            profile.setStatus(Status.BLOCK);
        }else if (profile.getStatus().equals(Status.BLOCK)){
            profile.setStatus(Status.ACTIVE);
        }
        profileRepository.updateProfileStatus(profile);
    }


}

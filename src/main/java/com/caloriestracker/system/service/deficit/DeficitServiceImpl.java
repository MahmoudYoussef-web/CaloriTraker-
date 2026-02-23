package com.caloriestracker.system.service.deficit;

import com.caloriestracker.system.dto.request.deficit.CalorieDeficitRequest;
import com.caloriestracker.system.entity.User;
import com.caloriestracker.system.entity.UserDeficit;
import com.caloriestracker.system.entity.UserProfile;
import com.caloriestracker.system.exception.ResourceNotFoundException;
import com.caloriestracker.system.repository.UserDeficitRepository;
import com.caloriestracker.system.repository.UserProfileRepository;
import com.caloriestracker.system.repository.UserRepository;
import com.caloriestracker.system.service.common.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
@Transactional
public class DeficitServiceImpl implements DeficitService {

    @PersistenceContext
    private EntityManager em;
    private final UserRepository userRepo;
    private final UserProfileRepository profileRepo;
    private final UserDeficitRepository deficitRepo;
    private final CalculationService calculationService;

    @Override
    public void setDeficit(Long userId,
                           CalorieDeficitRequest request) {

        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        UserProfile profile = profileRepo.findByUser_Id(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Profile not found")
                );

        // Calculate maintenance (TDEE)
        double bmr = calculationService.calculateBmr(
                profile.getGender(),
                profile.getAge(),
                profile.getHeightCm(),
                profile.getWeightKg()
        );

        double tdee = calculationService.calculateTdee(
                bmr,
                profile.getActivityLevel()
        );

        double deficitValue = request.getDeficit().doubleValue();

        UserDeficit deficit = deficitRepo
                .findByUserId(userId)
                .orElse(UserDeficit.builder()
                        .user(user)
                        .build());
    }
}
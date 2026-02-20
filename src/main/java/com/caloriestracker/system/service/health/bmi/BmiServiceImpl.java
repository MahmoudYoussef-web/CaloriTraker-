package com.caloriestracker.system.service.health.bmi;

import com.caloriestracker.system.dto.request.health.BmiRequest;
import com.caloriestracker.system.dto.response.health.BmiResponse;
import org.springframework.stereotype.Service;

@Service
public class BmiServiceImpl implements BmiService {

    @Override
    public BmiResponse calculate(BmiRequest request) {

        double heightM = request.getHeightCm() / 100.0;

        double bmi =
                request.getWeightKg() /
                        (heightM * heightM);

        String category;

        if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi < 25) {
            category = "Normal";
        } else if (bmi < 30) {
            category = "Overweight";
        } else {
            category = "Obese";
        }

        double bmr;

        if (request.getGender().name().equals("MALE")) {
            bmr = 10 * request.getWeightKg()
                    + 6.25 * request.getHeightCm()
                    - 5 * request.getAge()
                    + 5;
        } else {
            bmr = 10 * request.getWeightKg()
                    + 6.25 * request.getHeightCm()
                    - 5 * request.getAge()
                    - 161;
        }

        double multiplier = switch (request.getActivityLevel()) {
            case SEDENTARY -> 1.2;
            case LIGHT -> 1.375;
            case MODERATE -> 1.55;
            case ACTIVE -> 1.725;
            case VERY_ACTIVE -> 1.9;
        };

        double dailyCalories = bmr * multiplier;

        BmiResponse response = new BmiResponse();

        response.setBmi(Math.round(bmi * 100.0) / 100.0);
        response.setCategory(category);

        response.setDailyCalories(
                (double) Math.round(dailyCalories)
        );

        return response;
    }
}
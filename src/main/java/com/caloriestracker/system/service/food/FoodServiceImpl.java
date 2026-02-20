package com.caloriestracker.system.service.food;

import com.caloriestracker.system.dto.response.food.FoodResponse;
import com.caloriestracker.system.entity.Food;
import com.caloriestracker.system.exception.ResourceNotFoundException;
import com.caloriestracker.system.mapper.FoodMapper;
import com.caloriestracker.system.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepo;
    private final FoodMapper foodMapper;

    @Override
    public List<FoodResponse> getAllFoods() {

        return foodRepo.findAll()
                .stream()
                .map(foodMapper::toResponse)
                .toList();
    }

    @Override
    public FoodResponse getById(Long id) {

        Food food = foodRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found")
                );

        return foodMapper.toResponse(food);
    }

    @Override
    public FoodResponse getByName(String name) {

        Food food = foodRepo
                .findByNameIgnoreCase(name.trim())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found")
                );

        return foodMapper.toResponse(food);
    }
}
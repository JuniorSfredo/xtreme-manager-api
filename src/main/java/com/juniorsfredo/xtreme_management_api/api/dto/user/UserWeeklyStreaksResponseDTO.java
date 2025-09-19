package com.juniorsfredo.xtreme_management_api.api.dto.user;

import com.juniorsfredo.xtreme_management_api.api.dto.streak.WeeklyStreakDTO;

public record UserWeeklyStreaksResponseDTO(UserDetailsResponseDTO user, WeeklyStreakDTO weeklyStreak) {}

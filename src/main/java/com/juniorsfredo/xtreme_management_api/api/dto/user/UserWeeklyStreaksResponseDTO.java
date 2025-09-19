package com.juniorsfredo.xtreme_management_api.api.dto.user;

import com.juniorsfredo.xtreme_management_api.api.dto.streak.WeeklyStreakDTO;

public record UserWeeklyStreaksDTO(UserDetailsResponseDTO user, WeeklyStreakDTO weeklyStreak) {}

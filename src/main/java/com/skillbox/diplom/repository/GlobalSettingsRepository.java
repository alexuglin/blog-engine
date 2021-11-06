package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.GlobalSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalSettingsRepository extends JpaRepository<GlobalSetting, Integer> {
}

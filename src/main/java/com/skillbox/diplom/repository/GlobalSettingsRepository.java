package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.GlobalSetting;
import com.skillbox.diplom.model.enums.NameSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalSettingsRepository extends JpaRepository<GlobalSetting, Integer> {

    GlobalSetting findGlobalSettingByCode(NameSetting code);
}

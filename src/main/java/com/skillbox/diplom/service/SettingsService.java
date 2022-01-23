package com.skillbox.diplom.service;

import com.skillbox.diplom.model.GlobalSetting;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.model.enums.NameSetting;
import com.skillbox.diplom.model.enums.ValueSetting;
import com.skillbox.diplom.repository.GlobalSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final GlobalSettingsRepository globalSettingsRepository;
    private final Logger logger = Logger.getLogger(SettingsService.class);

    public ResponseEntity<Map<NameSetting, Boolean>> getSettings() {
        Map<NameSetting, Boolean> settingBooleanMap = globalSettingsRepository
                .findAll()
                .stream()
                .collect(Collectors.toMap(GlobalSetting::getCode,
                        globalSetting -> ValueSetting.YES == globalSetting.getValue()));
        return ResponseEntity.ok(settingBooleanMap);
    }

    @Transactional
    public ResponseEntity<ErrorResponse> editSettings(Map<NameSetting, Boolean> settings) {
        logger.info(settings);
        List<GlobalSetting> globalSettings = globalSettingsRepository.findAll();
        globalSettings.forEach(globalSetting -> globalSetting
                .setValue(settings.get(globalSetting.getCode()) ? ValueSetting.YES : ValueSetting.NO ));
        globalSettingsRepository.saveAll(globalSettings);
        return ResponseEntity.ok(new ErrorResponse());
    }
}

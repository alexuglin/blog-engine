package com.skillbox.diplom.model;

import com.skillbox.diplom.model.enums.NameSetting;
import com.skillbox.diplom.model.enums.ValueSetting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "global_settings")
public class GlobalSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private NameSetting code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ValueSetting value;
}
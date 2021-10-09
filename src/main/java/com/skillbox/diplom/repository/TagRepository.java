package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagRepository extends JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {
}

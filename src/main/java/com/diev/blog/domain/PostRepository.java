package com.diev.blog.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    Page<Post> findByCategoriesContains(Categories categories, Pageable pageable);

    Page<Post> findByTitleContainingOrContextContaining(String title, String content, Pageable pageable);
}

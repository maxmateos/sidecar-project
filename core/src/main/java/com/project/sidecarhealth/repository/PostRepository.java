package com.project.sidecarhealth.repository;

import com.project.sidecarhealth.entity.BlogPost;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<BlogPost, Long> {

  List<BlogPost> findByUserId(Long userId);

  Optional<BlogPost> findByIdAndUserId(Long id, Long userId);
}

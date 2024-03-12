package com.kindnesskattle.bddAtcProject.Repository;

import com.kindnesskattle.bddAtcProject.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {


//    List<Comment> findByPostId(long post_id);

}

package com.revature.postLike;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepo extends CrudRepository<PostLikes,Integer>{
	@Query(value="SELECT post_id, COUNT(*) as likes from post_likes group by post_id", nativeQuery=true)
    List<Object> getPostLikes();

    @Query(value="SELECT * FROM post_likes WHERE post_id = :pId AND user_id = :uId", nativeQuery=true)
    List<PostLikes> checkExistingLike(@Param("pId") int pId, @Param("uId") int uId);
}

package com.revature.postLike;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostLikeRepo extends CrudRepository<PostLikes,Integer>{
	@Query(value="SELECT post_id, COUNT(*) as likes from post_likes group by post_id", nativeQuery=true)
    List<Object> getPostLikes();

    @Query(value="SELECT * FROM post_likes WHERE post_id = :pId AND user_id = :uId", nativeQuery=true)
    List<PostLikes> checkExistingLike(@Param("pId") int pId, @Param("uId") int uId);
    
    @Query(value="SELECT * FROM post_likes WHERE user_id = :uId", nativeQuery=true)
    List<PostLikes> getLikesByUserId(@Param("uId") int uId);
    
//	@Query(value="INSERT into post_likes values (:pid, :uid)", nativeQuery=true)
//    List<Object> insertPostLike(@Param("pid") int pid, @Param("uid") int uid);
}

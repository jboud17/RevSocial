package com.revature.postLike;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostLikeService {
	
	@Autowired
	PostLikeRepo postLikeRepo;
	
	public boolean InsertRecord(int postId, int userId) {

        List<PostLikes> pl = postLikeRepo.checkExistingLike(postId, userId);
        if(pl.isEmpty()) {
        	PostLikes newPL = new PostLikes(postId, userId);
            postLikeRepo.save(newPL);
        } else {
            return false;
        }

        return true;
    }

    // might need to change up because Hql query is not simple
    public List<Object> getPostLikes() {
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) postLikeRepo.getPostLikes();
        return list;
    }
}

import { Injectable } from '@angular/core';
import { User } from '../models/User';

@Injectable()
export class AllPostsService {

  private allPosts: any = [];

  setPosts(posts: any[]){ 
    this.allPosts = posts;

    sessionStorage.setItem("allPosts", JSON.stringify(this.allPosts));
  }

  getAllPosts(): any[]{
    return JSON.parse(sessionStorage.getItem("allPosts"));
  }

}

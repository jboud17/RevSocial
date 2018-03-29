import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { CurrentUserService } from '../../services/current-user.service';
import { Title } from '@angular/platform-browser';
import { User } from '../../models/User';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { AllUsersService } from '../../services/all-users.service';
import { AllPostsService } from '../../services/all-posts.service';

@Component({
  selector: 'app-postview',
  templateUrl: './postview.component.html',
  styleUrls: ['./postview.component.css']
})
export class PostviewComponent implements OnInit {

  private currentPath: string = "";
  private viewedUser;
  private postsToDisplay: any[] = [];
  private postUrl: string = 'http://localhost:8080/SocialMedia/likePost';
  public s3: string = "https://s3.amazonaws.com/rev-grouptwo/images/";
  public path: string = location.pathname;
  public u: User = null;
  private allUsers: any;
  private allPosts: any[] = [];
  private headerString: string = "";

  constructor(private client: HttpClient, private currUser: CurrentUserService, private allUsersService: AllUsersService, private allPostsService: AllPostsService) {}

  ngOnInit() {
    this.currentPath = window.location.pathname.substring(8);
      
    this.allUsers = this.allUsersService.getAllUsers();
    this.allPosts = this.allPostsService.getAllPosts();
    this.headerString = "";
    
    if(this.currentPath != ""){
      for(var i=0; i<this.allUsers.length; i++){
        if(this.allUsers[i].username == this.currentPath.substring(1)){
          var viewedUser = this.allUsers[i]; //not using this.viewedUser to not have to cast it
          this.viewedUser = this.allUsers[i];
          var u : User = new User(viewedUser.user_id, viewedUser.first_name, viewedUser.last_name, viewedUser.email, viewedUser.imgHash);

          for(var j=0; j<this.allPosts.length; j++){
            if(this.allPosts[j].user.username == this.currentPath.substring(1)){
              this.postsToDisplay.push(this.allPosts[j]);
            }
          }

          if(this.postsToDisplay.length == 0){
            this.headerString = 'This user has no posts to display.';
          }
        }
      }
    }
    else if(window.location.pathname == '/profile'){
      for(var i=0; i<this.allPosts.length; i++){
        if(this.allPosts[i].user.user_id == this.currUser.getCurrentUser().id){
          this.postsToDisplay.push(this.allPosts[i]);
        }
      }

      if(this.postsToDisplay.length == 0){
        this.headerString = 'You have no posts to display.';
      }
    }
    else if(window.location.pathname == '/home'){
      this.postsToDisplay = this.allPosts;

      this.client.get('http://localhost:8080/SocialMedia/allPosts', {withCredentials:true}).subscribe(
        (succ: any) => {
          this.allPostsService.setPosts(succ);
          console.log(succ);
          this.postsToDisplay = succ;
          if(succ.length == 0){
            this.headerString = 'There are no posts in the database.';
          }
        }
      )
    }
  }

    likeBtnClick(postId, userId) {

      console.log("postId: " + postId + " userId: " + userId);
      
      const myheader = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
      const postbody = new HttpParams().set('postId', postId).set('userId', userId);
      this.client.post(this.postUrl, postbody, { headers: myheader }).subscribe(
        (succ: any) => {
          var r = succ[0];
          if(r.status == "green") {
            //increase likes count in html
            var id = "likes-count-"+postId;
            var html = document.getElementById(id).innerHTML;
            var countLikes = parseInt(html);
            countLikes++;
            var str = String(countLikes);
            document.getElementById(id).textContent = str;

            //like icon animation
            var iconId = "thumb-up-"+postId;
            var el = document.getElementById(iconId);   
            var padding = 5;
            var interval = setInterval(iconAnimation, 100);
            var count = 0;
          }
            
          function iconAnimation() {
            count++;
            if (count < 5) {
              padding += .5; 
              el.style.padding = padding + 'px';
            } else if (count < 9) {
              padding -= .5; 
              el.style.padding = padding + 'px';
            } else {
              clearInterval(interval);
            }
          }

        },
        err => {
          
          var id = "likes-count-"+postId;
          var html = document.getElementById(id).innerHTML;
          var countLikes = parseInt(html);
          countLikes++;
          var str = String(countLikes);
          document.getElementById(id).textContent = str;

            //like icon animation
            var iconId = "thumb-up-"+postId;
            var el = document.getElementById(iconId);   
            var padding = 5;
            var interval = setInterval(iconAnimation, 100);
            var count = 0;

          function iconAnimation() {
            count++;
            if (count < 5) {
              padding += .5; 
              el.style.padding = padding + 'px';
            } else if (count < 9) {
              padding -= .5; 
              el.style.padding = padding + 'px';
            } else {
              clearInterval(interval);
            }
          } 
        }
      );
      
    }

}
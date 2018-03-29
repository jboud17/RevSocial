import { Injectable } from '@angular/core';
import { User } from '../models/User';
// import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable()
export class CurrentUserService {
  constructor (){}

  private user: User;
  // private user = new BehaviorSubject(new User(null, null, null, null, null));
  // private userObservable = this.user.asObservable();

  setUser(user: User){ 
    this.user = user;
    //this.user.next(user);

    sessionStorage.setItem("user", JSON.stringify(this.user));

    console.log("user set to: {id:"+this.user.id+",first name:"+this.user.firstName+",last name:"+this.user.lastName+",email:"+this.user.email+",imgHash:"+this.user.imgHash+"}");
  }

  getCurrentUser(): User{
    return JSON.parse(sessionStorage.getItem("user"));
  }
}

import { Injectable } from '@angular/core';
import { User } from '../models/User';

@Injectable()
export class AllUsersService {

  private allUsers: User[] = [];

  setUsers(users: User[]){ 
    this.allUsers = users;

    sessionStorage.setItem("allUsers", JSON.stringify(this.allUsers));
  }

  getAllUsers(): User[]{
    return JSON.parse(sessionStorage.getItem("allUsers"));
  }

}

import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { CurrentUserService } from '../services/current-user.service';

@Injectable()
export class LoggedInGuard implements CanActivate {
  constructor(private router: Router, private currUser: CurrentUserService) { }
 
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if(this.currUser.getCurrentUser() != null){
      return true;
    }
    this.router.navigate(['/login']);
    
    alert("You must be logged in to view this page");
    return false;
  }

}

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login-page/login.component';
import { PostviewComponent } from './components/postview/postview.component';
import { NewpostComponent } from './components/newpost/newpost.component';
import { UpdateprofileComponent } from './components/updateprofile/updateprofile.component';
import { ProfilepageComponent } from './components/profile-page/profilepage.component';
import { HomeComponent } from './components/home-page/home.component';
import { ProfileHeaderComponent } from './components/profile-header/profile-header.component';
import { RegisterComponent } from './components/register-page/register.component';
import { LoggedInGuard } from './logged-in-guard/logged-in.guard';
import { CurrentUserService } from './services/current-user.service';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AllUsersService } from './services/all-users.service';
import { AllPostsService } from './services/all-posts.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PostviewComponent,
    NewpostComponent,
    UpdateprofileComponent,
    ProfilepageComponent,
    HomeComponent,
    ProfileHeaderComponent,
    RegisterComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: 'register', component: RegisterComponent},
      {path: 'login', component: LoginComponent},
      {path: 'home', component: HomeComponent},
      {path: 'profile', component: ProfilepageComponent, 
        children: [
          {path: '', redirectTo: '/profile', pathMatch: 'full'},
          {path: '**', component: LoginComponent}
        ]
      },
      {path: 'updateInfo', component: UpdateprofileComponent},
      //{path: 'home', component: HomeComponent, canActivate: [LoggedInGuard]},
      //{path: 'profile', component: ProfilepageComponent, canActivate: [LoggedInGuard]},
      {path: '', component: LoginComponent},
      {path: '**', pathMatch: 'full', component: PageNotFoundComponent}
    ])
  ],
  providers: [
    LoggedInGuard,
    CurrentUserService,
    AllUsersService,
    AllPostsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

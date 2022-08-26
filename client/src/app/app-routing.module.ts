import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppGuard } from './app-guard.service';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule),
  },
  {
    path: 'creator',
    loadChildren: () => import('./creator/creator.module').then(m => m.CreatorModule),
    canActivate: [AppGuard],
  },
  {
    path: '',
    component: HomeComponent,
    canActivate: [AppGuard],
  },
  {
    path: '**',
    redirectTo: '',
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

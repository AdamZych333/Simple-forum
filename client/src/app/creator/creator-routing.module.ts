import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CreatorComponent } from "./creator.component";

const routes: Routes = [
    {
        path: '',
        component: CreatorComponent,
    },
    {
        path: '**',
        redirectTo: '',
    }
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
  export class CreatorRoutingModule {}
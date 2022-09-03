import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CreatorComponent } from "./creator.component";
import { EditablePostResolver } from "./editable-post-resolver.service";

const routes: Routes = [
    {
        path: '',
        component: CreatorComponent,
    },
    {
        path: ':id',
        component: CreatorComponent,
        resolve: {
            post: EditablePostResolver,
        }
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
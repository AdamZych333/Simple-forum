import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreatorComponent } from './creator.component';
import { CreatorRoutingModule } from './creator-routing.module';
import { ReactiveFormsModule } from '@angular/forms';

import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MatChipsModule} from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { EditablePostResolver } from './editable-post-resolver.service';



@NgModule({
  declarations: [
    CreatorComponent,
  ],
  imports: [
    CommonModule,
    CreatorRoutingModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatChipsModule,
    MatIconModule,
  ],
  providers: [
    EditablePostResolver,
  ]
})
export class CreatorModule { }

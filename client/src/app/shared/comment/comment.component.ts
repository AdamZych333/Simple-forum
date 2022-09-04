import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { map } from 'rxjs';
import { AuthService, Comment } from 'src/app/core';

@Component({
  selector: 'app-comment[comment]',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CommentComponent {
  @Input() comment!: Comment;
  @Input() answering: boolean = true;
  @Input() showPost: boolean = false;
  @Input() showAuthor: boolean = true;
  showForm = false;
  editing = false;
  canEdit$ = this.authService.getCurrentUser().pipe(
    map(authUser => authUser.id === this.comment.userID || authUser.roles.map(r => r.name).includes('ADMIN')),
  )


  constructor(
    private authService: AuthService,
  ) { }


  onAnswerClick(){
    this.showForm = !this.showForm;
  }

  onDeleteClick(){

  }

  onEditClick(){
    this.editing = true;
  }

  onSuccessEdit(content: string){
    this.editing = false;
    this.comment = {...this.comment, content: content};
  }

  onCancelClick(){
    this.editing = false;
  }
}

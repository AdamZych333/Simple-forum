import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Comment, CommentService } from 'src/app/core';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-comment-form[postID]',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CommentFormComponent {
  @Input() postID!: number;
  @Input() parent: Comment | null = null;
  contentForm = new FormControl('', [Validators.required,Validators.minLength(5), Validators.maxLength(300)])
  @Output() onSuccess = new EventEmitter<Comment>();

  constructor(
    private commentService: CommentService,
    private userService: UserService,
  ) { }

  onSubmit(){
    if(this.contentForm.invalid) return;

    let parentID: number | null = null;
    let content = this.contentForm.value || '';
    if(this.parent){
      parentID = this.parent.id;
      this.userService.getUser(this.parent.userID).subscribe({
        next: (user) => {
          content = `@${user.name} ${content}`;
          this.addComment(content, parentID);
        }
      })
    }else{
      this.addComment(content, parentID);
    }

    
  }

  private addComment(content: string, parentID: number | null){
    this.commentService.addComment(this.postID, {
      content: content, 
      parentID: parentID,
    }).subscribe({
      next: (e) => {
        this.onSuccess.emit(e);
        this.contentForm.setValue('');
        this.contentForm.markAsUntouched();
      },
    })
  }

}

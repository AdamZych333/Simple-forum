import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Comment, CommentService } from 'src/app/core';

@Component({
  selector: 'app-comment-form[postID]',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.sass'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CommentFormComponent {
  @Input() postID!: number;
  @Input() parentID: number | null = null;
  contentForm = new FormControl('', [Validators.required,Validators.minLength(5), Validators.maxLength(400)])
  @Output() onSuccess = new EventEmitter<Comment>();

  constructor(
    private commentService: CommentService,
  ) { }

  onSubmit(){
    if(this.contentForm.invalid) return;

    this.commentService.addComment(this.postID, {
      content: this.contentForm.value || '', 
      parentID: this.parentID,
    }).subscribe({
      next: (e) => {
        this.onSuccess.emit(e);
        this.contentForm.setValue('');
        this.contentForm.markAsUntouched();
      },
    })
  }

}
